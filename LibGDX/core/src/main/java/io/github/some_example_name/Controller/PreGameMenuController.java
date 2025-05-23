package io.github.some_example_name.Controller;

import io.github.some_example_name.View.PreGameMenu;

public class PreGameMenuController {
    public static void handleButton(PreGameMenu menu) {
        if(menu.getHero().getSelected().equals("SHANA")) {
            menu.setHeroShow(menu.getAvatar0());
        } else if(menu.getHero().getSelected().equals("DIAMOND")) {
            menu.setHeroShow(menu.getAvatar1());
        } else if(menu.getHero().getSelected().equals("DASHER")) {
            menu.setHeroShow(menu.getAvatar2());
        } else if(menu.getHero().getSelected().equals("LILITH")) {
            menu.setHeroShow(menu.getAvatar3());
        } else if(menu.getHero().getSelected().equals("SCARLET")) {
            menu.setHeroShow(menu.getAvatar4());
        }

        if(menu.getGun().getSelected().equals("REVOLVER")) {
            menu.setGunShow(menu.getGun0());
        } else if(menu.getGun().getSelected().equals("SHOTGUN")) {
            menu.setGunShow(menu.getGun1());
        } else if(menu.getGun().getSelected().equals("SMG DUAL")) {
            menu.setGunShow(menu.getGun2());
        }
    }
}
