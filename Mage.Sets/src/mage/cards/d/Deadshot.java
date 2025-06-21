package mage.cards.d;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes, xenohedron
 */
public final class Deadshot extends CardImpl {

    public Deadshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Tap target creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));

        // It deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private Deadshot(final Deadshot card) {
        super(card);
    }

    @Override
    public Deadshot copy() {
        return new Deadshot(this);
    }
}
