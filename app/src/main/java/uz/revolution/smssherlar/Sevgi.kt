package uz.revolution.smssherlar

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_sevgi.view.*
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_sher.view.*
import kotlinx.android.synthetic.main.item_sher.view.matn
import kotlinx.android.synthetic.main.item_sher.view.sarlavha
import uz.revolution.smssherlar.adapters.MyAdapter
import uz.revolution.smssherlar.models.Sher


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Sevgi : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var data: ArrayList<Sher>? = null
    private val TAG = "AAAA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_sevgi, container, false)

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
                    val myClipboard = getSystemService(
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
                    0,
                    "Sizni qattiq sevgan yurak sizniki!",
                    resources.getString(R.string.matn1),
                    false,
                    false
                )
            )
        }
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sevgi().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}