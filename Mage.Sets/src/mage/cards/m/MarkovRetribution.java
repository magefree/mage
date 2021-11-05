package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarkovRetribution extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE);
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter2.add(new AnotherTargetPredicate(2));
    }

    public MarkovRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn));

        // • Target Vampire you control deals damage equal to its power to another target creature.
        Mode mode = new Mode(new DamageWithPowerFromOneToAnotherTargetEffect());
        TargetPermanent target = new TargetPermanent(filter);
        target.setTargetTag(1);
        mode.addTarget(target);
        target = new TargetPermanent(filter2);
        target.setTargetTag(2);
        mode.addTarget(target);
        this.getSpellAbility().addMode(mode);
    }

    private MarkovRetribution(final MarkovRetribution card) {
        super(card);
    }

    @Override
    public MarkovRetribution copy() {
        return new MarkovRetribution(this);
    }
}
