package uz.revolution.smssherlar

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_sevgi.view.*
import kotlinx.android.synthetic.main.item_sher.view.*
import uz.revolution.smssherlar.adapters.MyAdapter
import uz.revolution.smssherlar.models.Sher

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dostlik.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dostlik : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root:View
    lateinit var adapter: MyAdapter
    private var data: ArrayList<Sher>? = null
    private val TAG = "AAAA"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_dostlik, container, false)

        loadData()

        adapter = MyAdapter(data!!)
        root.rv.adapter = adapter

        root.back_to_home.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter.setOnItemClick(object : MyAdapter.OnItemClick {
            override fun onClick(sher: Sher, itemView: View, position: Int) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)

                val view = layoutInflater.inflate(R.layout.item_sher, null, false)
                view.sarlavha.text = sher.sarlavha
                view.matn.text = sher.matn
                if (sher.liked) {
                    view.like.setImageResource(R.drawable.ic_like)
                } else {
                    view.like.setImageResource(R.drawable.ic_like_empty)
                }

                view.message.setOnClickListener {
                    val smsBody = view.sarlavha.text.toString() + "\n\n" + view.matn.text.toString()
                    val sendIntent = Intent(Intent.ACTION_VIEW)
                    sendIntent.putExtra("sms_body", smsBody)
                    sendIntent.type = "vnd.android-dir/mms-sms"
                    startActivity(sendIntent)
                }

                view.share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            view.sarlavha.text.toString() + "\n\n" + view.matn.text.toString()
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }

                view.copy.setOnClickListener {
                    val myClipboard = ContextCompat.getSystemService(
                        context!!,
                        ClipboardManager::class.java
                    ) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText(
                        "simple text",
                        view.sarlavha.text.toString() + "\n\n" + view.matn.text.toString()
                    )
                    myClipboard.setPrimaryClip(clip)
                    Toast.makeText(root.context, "Copied", Toast.LENGTH_SHORT).show()
                }

                view.like.setOnClickListener {
                    if (sher.liked) {
                        view.like.setImageResource(R.drawable.ic_like_empty)
                        sher.liked = false
                        data!![position] = sher
                        adapter.notifyItemChanged(position)
                        Toast.makeText(root.context, "Unliked", Toast.LENGTH_SHORT).show()
                    } else {
                        view.like.setImageResource(R.drawable.ic_like)
                        sher.liked = true
                        data!![position] = sher
                        adapter.notifyItemChanged(position)
                        Toast.makeText(root.context, "Liked", Toast.LENGTH_SHORT).show()
                    }
                }


                dialog.setContentView(view)
                dialog.show()
            }
        })

        return root
    }

    private fun loadData() {
        data = ArrayList()
        for (i in 0..100) {
            data!!.add(
                Sher(
                    4,
                    "Sizni qattiq sevgan yurak sizniki!",
                    resources.getString(R.string.matn1),
                    true,
                    false
                )
            )
        }
    }
}