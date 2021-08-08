package com.kushagrawithmdg.gotrade.ui.Analytics

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.kushagrawithmdg.gotrade.Models.TimeRate
import com.kushagrawithmdg.gotrade.R
import com.kushagrawithmdg.gotrade.ViewModels.MainActivityViewModel
import java.time.Instant

class AnalyticsFragment : Fragment(){
    private val viewModel: MainActivityViewModel by activityViewModels()

    private lateinit var model: AnalyticsViewModel
    private lateinit var currencycode : String
    private lateinit var cryptolist : ArrayList<String>
    private lateinit var barChart: BarChart
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var root :View
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        model =ViewModelProvider(this).get(AnalyticsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_analytics, container, false)
editText = root.findViewById(R.id.editTextTextPersonName2)
        textView = root.findViewById(R.id.textView4)
barChart = root.findViewById(R.id.bar_chart)
        imageView = root.findViewById(R.id.button4)



        viewModel.data.observe(viewLifecycleOwner, Observer {
            cryptolist = viewModel._data.value!!
            currencycode = viewModel.data.value!!
            textView.text = viewModel.text.value

            Log.d("R", "Currency" + currencycode)
            val now: Instant = Instant.now()
            val endtime = now.toString()
            model.get(cryptolist[0], currencycode, endtime).observe(viewLifecycleOwner, Observer {
                makegraph(it)
            })
        })



imageView.setOnClickListener {
    val now: Instant = Instant.now()
    val endtime = now.toString()
    textView.text = editText.text
    model.load(editText.text.toString(), currencycode, endtime)
}




        return root
    }

    private fun makegraph(list: ArrayList<TimeRate>) {
        Log.d("AF", "makegraphcallto hua")
        val barOne = ArrayList<BarEntry>()
        val barTwo = ArrayList<BarEntry>()
        val barThree = ArrayList<BarEntry>()
        val barFour = ArrayList<BarEntry>()

        for (j in 1 until 9) {
            val ispecial: Float = j.toFloat()
            val isspecialrate1: Float = list[j - 1].rate_close.toFloat()
            val isspecialrate2: Float = list[j - 1].rate_open.toFloat()
            val isspecialrate3: Float = list[j - 1].rate_high.toFloat()
            val isspecialrate4: Float = list[j - 1].rate_low.toFloat()
            Log.d("AG", " ${list[j - 1].rate_close}")
            Log.d("AG", "$ispecial , $isspecialrate1 , $isspecialrate2 , $isspecialrate3, $isspecialrate4")
            barOne.add(BarEntry(ispecial, isspecialrate1))
            barTwo.add(BarEntry(ispecial, isspecialrate2))
            barThree.add(BarEntry(ispecial, isspecialrate3))
            barFour.add(BarEntry(ispecial, isspecialrate4))

        }

        val barDataSet1 = BarDataSet(barOne, "Closing")
        barDataSet1.color = Color.RED
        val barDataSet2 = BarDataSet(barTwo, "Opening")
        barDataSet2.color = Color.YELLOW
        val barDataSet3 = BarDataSet(barThree, "Highest")
        barDataSet3.color = Color.GREEN
        val barDataSet4 = BarDataSet(barFour, "Lowest")
        barDataSet4.color = Color.BLUE
        val  data = BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4)
        barChart.data = data
        val days = arrayOf("Today", "Yesterday", "2ndLastDay", "3rdLastDay", "4thLastDay", "5thLastDay", "6thLastDay", "7thLastDay")


        val xAxis = barChart.xAxis
        xAxis.valueFormatter=IndexAxisValueFormatter(days)
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        barChart.isDragEnabled = true
        barChart.setVisibleXRangeMaximum(2f)
        val barSpace = 0.05f
        val groupspace = 0.16f

        data.barWidth = 0.16f
        barChart.xAxis.axisMinimum = 0f
        barChart.xAxis.axisMaximum = (0+ barChart.barData.getGroupWidth(groupspace, barSpace))*8
        barChart.axisLeft.axisMinimum = 0f
        barChart.groupBars(0f, groupspace, barSpace)
        barChart.invalidate()

    }

}