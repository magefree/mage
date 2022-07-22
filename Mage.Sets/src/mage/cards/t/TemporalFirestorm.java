package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemporalFirestorm extends CardImpl {

    public TemporalFirestorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Kicker {1}{W} and/or {1}{U}
        KickerAbility kickerAbility = new KickerAbility("{1}{W}");
        kickerAbility.addKickerCost("{1}{U}");
        this.addAbility(kickerAbility);

        // Choose up to X creatures and/or planeswalkers you control, where X is the number of times this spell was kicked. Those permanents phase out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect().setText("choose up to X creatures and/or " +
                "planeswalkers you control, where X is the number of times this spell was kicked. Those permanents phase out"));
        this.getSpellAbility().setTargetAdjuster(TemporalFirestormAdjuster.instance);

        // Temporal Firestorm deals 5 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(
                5, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
        ).setText("{this} deals 5 damage to each creature and each planeswalker"));
    }

    private TemporalFirestorm(final TemporalFirestorm card) {
        super(card);
    }

    @Override
    public TemporalFirestorm copy() {
        return new TemporalFirestorm(this);
    }
}

enum TemporalFirestormAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creatures and/or planeswalkers you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int kickedCount = MultikickerCount.instance.calculate(game, ability, null);
        if (kickedCount > 0) {
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(0, kickedCount, filter));
        }
    }
}
