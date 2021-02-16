package uz.revolution.smssherlar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import uz.revolution.smssherlar.models.Sher

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        loadData()
        categorizeData()
    }

    lateinit var root:View
    lateinit var data: ArrayList<Sher>
    private var dataSevgi: ArrayList<Sher>?=null
    private var dataSoginch: ArrayList<Sher>?=null
    private var dataTabrik: ArrayList<Sher>?=null
    private var dataOta: ArrayList<Sher>?=null
    private var dataDostlik: ArrayList<Sher>?=null
    private var dataHazil: ArrayList<Sher>?=null
    private var dataYangilar: ArrayList<Sher>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        root.card_sevgi_sheralari.setOnClickListener {
            root.findNavController().navigate(R.id.sevgi)
        }

        root.card_soginch_armon.setOnClickListener {
            root.findNavController().navigate(R.id.soginch)
        }

        root.card_tabrik_sherlari.setOnClickListener {
            root.findNavController().navigate(R.id.tabrik)
        }

        root.card_ota_ona.setOnClickListener {
            root.findNavController().navigate(R.id.ota)
        }

        root.card_dostlik.setOnClickListener {
            root.findNavController().navigate(R.id.dostlik)
        }

        root.card_hazil.setOnClickListener {
            root.findNavController().navigate(R.id.hazil)
        }

        root.card_yangilar.setOnClickListener {
            root.findNavController().navigate(R.id.yangilar)
        }

        root.card_saqlanganlar.setOnClickListener {
            root.findNavController().navigate(R.id.saqlanganlar)
        }

        return root
    }

    private fun categorizeData() {
        dataSevgi = ArrayList()
        dataSoginch = ArrayList()
        dataTabrik = ArrayList()
        dataOta = ArrayList()
        dataDostlik = ArrayList()
        dataHazil = ArrayList()
        dataYangilar=ArrayList()

        for (i in 0 until data.size) {
            val kategoriya=data[i].kategoriya
            val list=data[i]

            when (kategoriya) {
                0 -> {
                    dataSevgi!!.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                1 -> {
                    dataSoginch?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                2 -> {
                    dataTabrik?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                3 -> {
                    dataOta?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                4 -> {
                    dataDostlik?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                5 -> {
                    dataHazil?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
                6 -> {
                    dataYangilar?.add(Sher(list.kategoriya, list.sarlavha, list.matn, list.liked,list.yangilar))
                }
            }
        }


    }

    private fun loadData() {
        data = ArrayList()
        for (i in 0 until 100) {
            when (i % 6) {
                0 -> {
                    data.add(Sher(0,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                1 -> {
                    data.add(Sher(1,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                2 -> {
                    data.add(Sher(2,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                3 -> {
                    data.add(Sher(3,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                4 -> {
                    data.add(Sher(4,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                5 -> {
                    data.add(Sher(5,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,false))
                }
                6->{
                    data.add(Sher(6,"Sizni qattiq sevgan yurak sizniki!", R.string.matn1.toString(),true,true))
                }
            }

        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}