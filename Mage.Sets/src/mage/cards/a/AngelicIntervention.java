package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicIntervention extends CardImpl {

    public AngelicIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature or planeswalker you control gains protection from colorless or from the color of your choice until end of turn. If it's a creature, put a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new AngelicInterventionEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER));
    }

    private AngelicIntervention(final AngelicIntervention card) {
        super(card);
    }

    @Override
    public AngelicIntervention copy() {
        return new AngelicIntervention(this);
    }
}

class AngelicInterventionEffect extends OneShotEffect {

    private static final FilterObject filter = new FilterObject("colorless");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    AngelicInterventionEffect() {
        super(Outcome.Benefit);
        staticText = "target creature or planeswalker you control gains protection from colorless " +
                "or from the color of your choice until end of turn. If it's a creature, put a +1/+1 counter on it";
    }

    private AngelicInterventionEffect(final AngelicInterventionEffect effect) {
        super(effect);
    }

    @Override
    public AngelicInterventionEffect copy() {
        return new AngelicInterventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || player == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Give the targeted permanent protection from colorless?", null,
                "Yes", "No (choose a color instead)", source, game
        )) {
            game.addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn), source);
        } else {
            game.addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), source);
        }
        if (permanent.isCreature(game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
