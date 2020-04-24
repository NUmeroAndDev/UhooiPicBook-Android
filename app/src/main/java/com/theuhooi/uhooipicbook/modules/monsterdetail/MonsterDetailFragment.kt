package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.theuhooi.uhooipicbook.R
import kotlinx.android.synthetic.main.fragment_monster_detail.view.*

class MonsterDetailFragment : Fragment() {

    // MARK: Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    // MARK: View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monster_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.icon_imageview.load(this.args.monster.iconUrlString)
        view.name_textview.text = this.args.monster.name
        view.description_textview.text = this.args.monster.description
    }

}
