package com.alice.rodexapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alice.rodexapp.R

class ReportPageFragment : Fragment() {

    private lateinit var etDamageType: EditText
    private lateinit var etTotal: EditText
    private lateinit var etLocation: EditText
    private lateinit var etNumberRoad: EditText
    private lateinit var etNumberType: EditText
    private lateinit var ivImage: ImageView
    private lateinit var tvPotholes: TextView
    private lateinit var tvStatistic: TextView
    private lateinit var tvNumberRoad: TextView
    private lateinit var tvNumberType: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        etDamageType = view.findViewById(R.id.etDamageType)
        etTotal = view.findViewById(R.id.etTotal)
        etLocation = view.findViewById(R.id.etLocation)
        etNumberRoad = view.findViewById(R.id.etNumberRoad)
        etNumberType = view.findViewById(R.id.etNumberType)
        ivImage = view.findViewById(R.id.ivImage)
        tvPotholes = view.findViewById(R.id.tvPotholes)
        tvStatistic = view.findViewById(R.id.tvStatistic)
        tvNumberRoad = view.findViewById(R.id.tvNumberRoad)
        tvNumberType = view.findViewById(R.id.tvNumberType)

    }
}