package smb.converter;

public enum TrackListing {
    TITLE_INTRO       ("Title (Intro)"                  , "intro_music.wav"            , null, 0, 0),
    TITLE_SCREEN      ("Title Screen"                   , "smbtitleloop.wav"           , null, 0, 0),

    FOREST_MENU       ("Forest Menu (Light World)"      , "forestmenuloop.wav"         , null, 0, 0),
    FOREST_MENU_DARK  ("Forest Menu (Dark World)"       , "evilforestmenuloop.wav"     , null, 0, 0),
    FOREST_LIGHT      ("Forest (Light World)"           , "smbforest5.wav"             , null, 0, 0),
    FOREST_DARK       ("Forest (Dark World)"            , "smbforestalt.wav"           , null, 0, 0),
    FOREST_BOSS       ("Little Slugger"                 , "bawsslewp.wav"              , null, 0, 0),
    FOREST_RETRO      ("Forest (Retro)"                 , "forestretro3.wav"           , null, 0, 0),

    HOSPITAL_MENU     ("Hospital Menu (Light World)"    , "hmenuloop.wav"              , null, 0, 0),
    HOSPITAL_MENU_DARK("Hospital Menu (Dark World)"     , "hevilmenuloop.wav"          , null, 0, 0),
    HOSPITAL_LIGHT    ("Hospital (Light World)"         , "ch2track.wav"               , null, 0, 0),
    HOSPITAL_DARK     ("Hospital (Dark World)"          , "cht2trackalt.wav"           , null, 0, 0),
    HOSPITAL_BOSS     ("C.H.A.D."                       , "boss2.wav"                  , null, 0, 0),
    HOSPITAL_RETRO    ("Hospital (Retro)"               , "ch2retro.wav"               , null, 0, 0),

    FACTORY_MENU      ("Salt Factory Menu (Light World)", "factorymenu.wav"            , null, 0, 0),
    FACTORY_MENU_DARK ("Salt Factory Menu (Dark World)" , "evilfactorymenu.wav"        , null, 0, 0),
    FACTORY_LIGHT     ("Salt Factory (Light World)"     , "factory.wav"                , null, 0, 0),
    FACTORY_DARK      ("Salt Factory (Dark World)"      , "altfactory.wav"             , null, 0, 0),
    FACTORY_BOSS      ("Brownie"                        , "boss3.wav"                  , null, 0, 0),
    FACTORY_RETRO     ("Salt Factory (Retro)"           , "retrofactory32khz_music.wav", null, 0, 0),

    HELL_MENU         ("Hell Menu (Light World)"        , "menunormalhell.wav"         , null, 0, 0),
    HELL_MENU_DARK    ("Hell Menu (Dark World)"         , "menuhardhell.wav"           , null, 0, 0),
    HELL_LIGHT        ("Hell (Light World)"             , "hell.wav"                   , null, 0, 0),
    HELL_DARK         ("Hell (Dark World)"              , "hardhell.wav"               , null, 0, 0),
    HELL_BOSS         ("Little Horn"                    , "boss4.wav"                  , null, 0, 0),
    HELL_RETRO        ("Hell (Retro)"                   , "retrohell_music.wav"        , null, 0, 0), // Could also be retrohell.wav

    RAPTURE_MENU      ("Rapture Menu (Light World)"     , "ch5menu.wav"                , null, 0, 0),
    RAPTURE_MENU_DARK ("Rapture Menu (Dark World)"      , "ch5menuhard.wav"            , null, 0, 0),
    RAPTURE_LIGHT     ("Rapture (Light World)"          , "ch5.wav"                    , null, 0, 0),
    RAPTURE_DARK      ("Rapture (Dark World)"           , "ch5hard.wav"                , null, 0, 0),
    RAPTURE_BOSS      ("Larries Lament"                 , "boss5.wav"                  , null, 0, 0),
    RAPTURE_RETRO     ("Rapture (Retro)"                , "ch5retro_music.wav"         , null, 0, 0),

    END_MENU          ("End Menu (Light World)"         , "ch6normalmenu.wav"          , null, 0, 0),
    END_MENU_DARK     ("End Menu (Dark World)"          , "ch6hardmenu.wav"            , null, 0, 0),
    END_LIGHT         ("End (Light World)"              , "ch6.wav"                    , null, 0, 0),
    //END_DARK doesn't exist
    END_BOSS          ("Dr. Fetus"                      , "boss6.wav"                  , null, 0, 0),

    COTTON_MENU       ("Cotton Alley Menu (Light World)", "bmenu.wav"                  , null, 0, 0),
    COTTON_MENU_DARK  ("Cotton Alley Menu (Dark World)" , "bmenuhard.wav"              , null, 0, 0),
    COTTON_LIGHT      ("Cotton Alley (Light World)"     , "bgirl.wav"                  , null, 0, 0),
    //COTTON_DARK doesn't exist
    ;
    public final String name;
    public final String pcName;
    public final String url;
    public final double trimStart;
    public final double trimEnd;

    TrackListing(String name, String pcName, String url, double trimStart, double trimEnd) {
        this.name = name;
        this.pcName = pcName;
        this.url = url;
        this.trimStart = trimStart;
        this.trimEnd = trimEnd;
    }
}
