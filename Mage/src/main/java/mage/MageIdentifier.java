package mage;

/**
 * Used to identify specific actions/events and to be able to assign them to the
 * correct watcher or other processing.
 *
 * @author LevelX2
 */
public enum MageIdentifier {
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
    OneWithTheMultiverseWatcher,
    JohannApprenticeSorcererWatcher,
    KaghaShadowArchdruidWatcher,
    CourtOfLocthwainWatcher("Without paying manacost");

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
