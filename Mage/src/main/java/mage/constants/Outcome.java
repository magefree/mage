package mage.constants;

/**
 * @author North
 */
public enum Outcome {
    Damage(false),
    DestroyPermanent(false),
    BoostCreature(true),
    UnboostCreature(false),
    AddAbility(true),
    LoseAbility(false),
    GainLife(true),
    LoseLife(false),
    ExtraTurn(true),
    BecomeCreature(true),
    PutCreatureInPlay(true),
    PutCardInPlay(true),
    PutLandInPlay(true),
    GainControl(false),
    DrawCard(true),
    Discard(false),
    Sacrifice(false),
    PlayForFree(true),
    ReturnToHand(false),
    Exile(false),
    Protect(true),
    PutManaInPool(true),
    Regenerate(true),
    PreventDamage(true), // TODO: check good or bad
    PreventCast(false),
    RedirectDamage(true), // TODO: check good or bad
    Tap(false),
    Transform(true),
    Untap(true),
    Win(true),
    Copy(true, true),
    Benefit(true),
    Detriment(false),
    Neutral(true),
    Removal(false),
    AIDontUseIt(false),
    Vote(true); // TODO: check good or bad

    // good or bad effect for TARGET, not targeting player (for AI usage)
    // AI sorting targets by priorities (own or opponents) and selects most valueable or weakest
    private final boolean good;

    // no different between own or opponent targets (example: copy must choose from all permanents)
    private boolean canTargetAll;

    Outcome(boolean good) {
        this.good = good;
    }

    Outcome(boolean good, boolean canTargetAll) {
        this.good = good;
        this.canTargetAll = canTargetAll;
    }

    public boolean isGood() {
        return good;
    }

    public boolean isCanTargetAll() {
        return canTargetAll;
    }

    public static Outcome inverse(Outcome outcome) {
        // inverse bad/good effect
        if (outcome.isGood()) {
            return Outcome.Detriment;
        } else {
            return Outcome.Benefit;
        }
    }
}
