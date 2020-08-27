package com.example.coronahelp.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import com.example.coronahelp.R
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView
import edmt.dev.advancednestedscrollview.MyViewGroupUtils
import kotlinx.android.synthetic.main.fragment_maps.view.*

class CustomBehavior(context: Context, attrs:AttributeSet):CoordinatorLayout.Behavior<NestedScrollView>(context,attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        return dependency.id == R.id.toolbar_container
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: NestedScrollView, layoutDirection: Int): Boolean {
        parent.onLayoutChild(child, layoutDirection)

        //centering the FAB vertically along the top edge of the card
        val fabHalfHeight = child.findViewById<View>(R.id.fab_add).height/2
        setTopMargin(child.findViewById<CardView>(R.id.card_view),fabHalfHeight)

        //Max height of a recyclerView to ensure that the card will never overlap the toolbar as it scrolls
        var rvMaxHeight = (child.height - fabHalfHeight - child.findViewById<View>(R.id.card_title).height)
        var rv = child.findViewById<MaxHeightRecyclerView>(R.id.card_recycler_view)
        rv.setMaxHeight(rvMaxHeight)

        val cardContainer = child.findViewById<View>(R.id.card_container)
        val toolbarContainerHeight = parent.getDependencies(child)[0].height
        setPaddingTop(cardContainer, rvMaxHeight-toolbarContainerHeight)

        ViewCompat.offsetTopAndBottom(child, toolbarContainerHeight)

        setPaddingBottom(rv, toolbarContainerHeight)

        return true
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: NestedScrollView, ev: MotionEvent): Boolean {
        return (ev.actionMasked == MotionEvent.ACTION_DOWN
                && isTouchInChildBounds(parent, child, ev)
                && !isTouchInChildBounds(parent, child.findViewById(R.id.card_view), ev)
                && !isTouchInChildBounds(parent, child.findViewById(R.id.fab_add),ev))
    }

    private fun isTouchInChildBounds(parent: ViewGroup, child: View, ev: MotionEvent): Boolean {
        return MyViewGroupUtils.isPointInChildBounds(parent, child, ev.x.toInt(), ev.y.toInt())
    }

    private fun setPaddingBottom(view: View, bottom: Int) {
        if(view!!.paddingBottom != bottom){
            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, bottom)
        }
    }

    private fun setPaddingTop(view: View?, top: Int){
        if(view!!.paddingTop != top){
            view.setPadding(view.paddingLeft, top, view.paddingRight, view.paddingBottom)
        }
    }

    private fun setTopMargin(view: View?, top: Int){
        val lp = view!!.layoutParams as ViewGroup.MarginLayoutParams
        if(lp.topMargin != top)
        {
            lp.topMargin = top
            view.layoutParams = lp
        }
    }

}