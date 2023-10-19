package mage;

/**
 * Used to identify specific actions/events and to be able to assign them to the
 * correct watcher or other processing.
 *
 * @author LevelX2, Susucr
 */
public enum MageIdentifier {
    // No special behavior. Cleaner than null as a default.
    Default,

    // -------------------------------- //
    //       spell cast watchers        //
    // -------------------------------- //
    //
    // All those are used by a watcher to track spells cast using a matching MageIdentifier way.
    //
    // e.g. [[Johann, Apprentice Sorcerer]]
    // "Once each turn, you may cast an instant or sorcery spell from the top of your library."
    //
    CastFromGraveyardOnceWatcher,
    CemeteryIlluminatorWatcher,
    GisaAndGeralfWatcher,
    DanithaNewBenaliasLightWatcher,
    HaukensInsightWatcher,
    KaradorGhostChieftainWatcher,
    KessDissidentMageWatcher,
    MuldrothaTheGravetideWatcher,
    ShareTheSpoilsWatcher,
    WishWatcher,
    GlimpseTheCosmosWatcher,
    SerraParagonWatcher,
    OneWithTheMultiverseWatcher("Without paying manacost"),
    JohannApprenticeSorcererWatcher,
    KaghaShadowArchdruidWatcher,
    CourtOfLocthwainWatcher("Without paying manacost"),

    // ----------------------------//
    //       alternate casts       //
    // ----------------------------//
    //
    // All those are used to link (cost) modification only when cast
    // using an AsThough with the matching MageIdentifier.
    //
    // e.g. [[Bolas's Citadel]]
    // """
    // You may look at the top card of your library any time.
    //
    // You may play lands and cast spells from the top of your library.
    // If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
    // """
    //
    // If there are other ways to cast from the top of the library, then the MageIdentifier being different
    // means that the alternate cast won't apply to the other ways to cast.
    BolassCitadelAlternateCast,
    RisenExectutionerAlternateCast,
    DemilichAlternateCast,
    DemonicEmbraceAlternateCast,
    FalcoSparaPactweaverAlternateCast,
    HelbruteAlternateCast,
    MaestrosAscendencyAlternateCast,
    NashiMoonSagesScionAlternateCast,
    RafinnesGuidanceAlternateCast,
    RonaSheoldredsFaithfulAlternateCast,
    ScourgeOfNelTothAlternateCast,
    SqueeDubiousMonarchAlternateCast,
    WorldheartPhoenixAlternateCast,
    XandersPactAlternateCast;

    /**
     * Additional text if there is need to differentiate two very similar effects
     * from the same source in the UI.
     * See [[Court of Lochtwain]] for an example.
     * """
     * At the beginning of your upkeep, exile the top card of target opponent’s library.
     * You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it.
     * If you're the monarch, until end of turn, you may cast a spell from among cards exiled with
     * Court of Locthwain without paying its mana cost.
     * """
     */
    private final String additionalText;

    MageIdentifier() {
        this("");
    }

    MageIdentifier(String additionalText) {
        this.additionalText = additionalText;
    }

    public String getAdditionalText() {
        return this.additionalText;
    }
}
