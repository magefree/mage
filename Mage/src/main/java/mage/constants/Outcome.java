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
    PreventDamage(true),
    RedirectDamage(true),
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
    Vote(true);
    private final boolean good; // good or bad for targets in current effect
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
}
