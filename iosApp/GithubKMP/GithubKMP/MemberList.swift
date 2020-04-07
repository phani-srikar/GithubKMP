//
//  MemberList.swift
//  GithubKMP
//
//  Created by Edupuganti, Phani Srikar on 4/6/20.
//  Copyright © 2020 Edupuganti Phani Srikar. All rights reserved.
//

import Foundation
import shared

class MemberList {
    var members: [Member] = []
    
    func updateMembers(_ newMembers: [Member]) {
        members.removeAll()
        members.append(contentsOf: newMembers)
    }
}
