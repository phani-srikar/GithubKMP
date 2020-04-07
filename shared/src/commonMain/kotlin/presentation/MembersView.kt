package com.example.presentation

import com.example.model.Member

interface MembersView : BaseView {
    var isUpdating: Boolean
    fun onUpdate(members: List<Member>)
}