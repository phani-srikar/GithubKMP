//
//  AppDelegate.swift
//  GithubKMP
//
//  Created by Edupuganti, Phani Srikar on 4/5/20.
//  Copyright © 2020 Edupuganti Phani Srikar. All rights reserved.
//

import UIKit
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    public lazy var dataRepository = { MembersDataRepository(api: GitHubApi()) }()
    
    static var appDelegate: AppDelegate {
        return UIApplication.shared.delegate as! AppDelegate
    }
}

