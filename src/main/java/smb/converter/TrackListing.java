package smb.converter;

public enum TrackListing {
    TITLE_INTRO       ("Title (Intro)"                  , "intro_music.wav"            , "https://ridiculon.bandcamp.com/track/bologna-pogna-title-intro"                      , 0, 0),
    TITLE_SCREEN      ("Title Screen"                   , "smbtitleloop.wav"           , "https://ridiculon.bandcamp.com/track/coming-to-a-deli-near-you-title-screen"         , 0, 0),

    FOREST_MENU       ("Forest Menu (Light World)"      , "forestmenuloop.wav"         , "https://ridiculon.bandcamp.com/track/gristletoe-forest-menu-light-dark"              , 0, 0),
    FOREST_MENU_DARK  ("Forest Menu (Dark World)"       , "evilforestmenuloop.wav"     , "https://ridiculon.bandcamp.com/track/gristletoe-forest-menu-light-dark"              , 0, 0),
    FOREST_LIGHT      ("Forest (Light World)"           , "smbforest5.wav"             , "https://ridiculon.bandcamp.com/track/jam-banjovi-forest-light"                       , 0, 0),
    FOREST_DARK       ("Forest (Dark World)"            , "smbforestalt.wav"           , "https://ridiculon.bandcamp.com/track/dark-meat-forest-dark"                          , 0, 0),
    FOREST_BOSS       ("Little Slugger"                 , "bawsslewp.wav"              , "https://ridiculon.bandcamp.com/track/throw-another-banjo-on-the-fire-forest-boss"    , 0, 0),
    FOREST_RETRO      ("Forest (Retro)"                 , "forestretro3.wav"           , "https://ridiculon.bandcamp.com/track/meat-rainbow-get-on-the-dancefloor-forest-retro", 0, 0),

    HOSPITAL_MENU     ("Hospital Menu (Light World)"    , "hmenuloop.wav"              , "https://scattle.bandcamp.com/track/amputation-hospital-menu-light"                   , 0, 0),
    HOSPITAL_MENU_DARK("Hospital Menu (Dark World)"     , "hevilmenuloop.wav"          , "https://scattle.bandcamp.com/track/overdose-hospital-menu-dark"                      , 0, 0),
    HOSPITAL_LIGHT    ("Hospital (Light World)"         , "ch2track.wav"               , "https://scattle.bandcamp.com/track/bedside-manner-hospital-light-2"                  , 0, 0),
    HOSPITAL_DARK     ("Hospital (Dark World)"          , "cht2trackalt.wav"           , "https://scattle.bandcamp.com/track/lights-out-hospital-dark"                         , 0, 0),
    HOSPITAL_BOSS     ("C.H.A.D."                       , "boss2.wav"                  , "https://scattle.bandcamp.com/track/165-hospital-boss"                                , 0, 0),
    HOSPITAL_RETRO    ("Hospital (Retro)"               , "ch2retro.wav"               , "https://scattle.bandcamp.com/track/doctors-orders-hospital-retro"                    , 0, 0),

    FACTORY_MENU      ("Salt Factory Menu (Light World)", "factorymenu.wav"            , "https://scattle.bandcamp.com/track/the-red-sea-salt-factory-menu-light"              , 0, 0),
    FACTORY_MENU_DARK ("Salt Factory Menu (Dark World)" , "evilfactorymenu.wav"        , "https://scattle.bandcamp.com/track/dry-rub-salt-factory-menu-dark"                   , 0, 0),
    FACTORY_LIGHT     ("Salt Factory (Light World)"     , "factory.wav"                , "https://scattle.bandcamp.com/track/hypertension-salt-factory-light"                  , 0, 0),
    FACTORY_DARK      ("Salt Factory (Dark World)"      , "altfactory.wav"             , "https://scattle.bandcamp.com/track/the-seasoning-salt-factory-dark"                  , 0, 0),
    FACTORY_BOSS      ("Brownie"                        , "boss3.wav"                  , "https://scattle.bandcamp.com/track/dash-assault-salt-factory-boss"                   , 0, 0),
    FACTORY_RETRO     ("Salt Factory (Retro)"           , "retrofactory32khz_music.wav", "https://scattle.bandcamp.com/track/white-gold-salt-factory-retro"                    , 0, 0),

    HELL_MENU         ("Hell Menu (Light World)"        , "menunormalhell.wav"         , "https://ridiculon.bandcamp.com/track/hellway-to-hell-hell-menu-light-dark"           , 0, 0),
    HELL_MENU_DARK    ("Hell Menu (Dark World)"         , "menuhardhell.wav"           , "https://ridiculon.bandcamp.com/track/hellway-to-hell-hell-menu-light-dark"           , 0, 0),
    HELL_LIGHT        ("Hell (Light World)"             , "hell.wav"                   , "https://ridiculon.bandcamp.com/track/secret-santana-hell-light"                      , 0, 0),
    HELL_DARK         ("Hell (Dark World)"              , "hardhell.wav"               , "https://ridiculon.bandcamp.com/track/hell-toup-hell-dark"                            , 0, 0),
    HELL_BOSS         ("Little Horn"                    , "boss4.wav"                  , "https://ridiculon.bandcamp.com/track/fasten-your-meatbelts-hell-boss"                , 0, 0),
    HELL_RETRO        ("Hell (Retro)"                   , "retrohell_music.wav"        , "https://ridiculon.bandcamp.com/track/mintz-meat-hell-retro"                          , 0, 0), // Could also be retrohell.wav

    RAPTURE_MENU      ("Rapture Menu (Light World)"     , "ch5menu.wav"                , "https://ridiculon.bandcamp.com/track/meat-you-in-hell-rapture-menu-light-dark"       , 0, 0),
    RAPTURE_MENU_DARK ("Rapture Menu (Dark World)"      , "ch5menuhard.wav"            , "https://ridiculon.bandcamp.com/track/meat-you-in-hell-rapture-menu-light-dark"       , 0, 0),
    RAPTURE_LIGHT     ("Rapture (Light World)"          , "ch5.wav"                    , "https://ridiculon.bandcamp.com/track/steak-thru-the-heart-rapture-light"             , 0, 0),
    RAPTURE_DARK      ("Rapture (Dark World)"           , "ch5hard.wav"                , "https://ridiculon.bandcamp.com/track/dream-meater-rapture-dark"                      , 0, 0),
    RAPTURE_BOSS      ("Larries Lament"                 , "boss5.wav"                  , "https://ridiculon.bandcamp.com/track/meat-yer-maker-rapture-boss"                    , 0, 0),
    RAPTURE_RETRO     ("Rapture (Retro)"                , "ch5retro_music.wav"         , "https://ridiculon.bandcamp.com/track/meat-me-in-compton-rapture-retro"               , 0, 0),

    END_MENU          ("End Menu (Light World)"         , "ch6normalmenu.wav"          , "https://ridiculon.bandcamp.com/track/leather-glove-well-done-end-menu-light-dark"    , 0, 0),
    END_MENU_DARK     ("End Menu (Dark World)"          , "ch6hardmenu.wav"            , "https://ridiculon.bandcamp.com/track/leather-glove-well-done-end-menu-light-dark"    , 0, 0),
    END_LIGHT         ("End (Light World)"              , "ch6.wav"                    , "https://ridiculon.bandcamp.com/track/meat-continuum-end-light"                       , 0, 0),
    //END_DARK doesn't exist
    END_BOSS          ("Dr. Fetus"                      , "boss6.wav"                  , "https://ridiculon.bandcamp.com/track/meatal-acropolis-end-boss"                      , 0, 0),

    COTTON_MENU       ("Cotton Alley Menu (Light World)", "bmenu.wav"                  , "https://laurashigihara.bandcamp.com/track/cotton-alley-light-menu"                   , 0, 0),
    COTTON_MENU_DARK  ("Cotton Alley Menu (Dark World)" , "bmenuhard.wav"              , "https://laurashigihara.bandcamp.com/track/cotton-alley-dark-menu"                    , 0, 0),
    COTTON_LIGHT      ("Cotton Alley (Light World)"     , "bgirl.wav"                  , "https://laurashigihara.bandcamp.com/track/cotton-candy-cotton-alley-light"           , 0, 0),
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
