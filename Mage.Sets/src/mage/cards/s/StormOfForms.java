package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormOfForms extends CardImpl {

    public StormOfForms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // When you cast this spell, copy it for each kind of counter among permanents you control. You may choose new targets for the copies.
        this.addAbility(new CastSourceTriggeredAbility(new StormOfFormsEffect()));

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private StormOfForms(final StormOfForms card) {
        super(card);
    }

    @Override
    public StormOfForms copy() {
        return new StormOfForms(this);
    }
}

class StormOfFormsEffect extends OneShotEffect {

    StormOfFormsEffect() {
        super(Outcome.Benefit);
        staticText = "copy it for each kind of counter among permanents you control. " +
                "You may choose new targets for the copies";
    }

    private StormOfFormsEffect(final StormOfFormsEffect effect) {
        super(effect);
    }

    @Override
    public StormOfFormsEffect copy() {
        return new StormOfFormsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        int amount = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        source.getControllerId(), source, game
                ).stream()
                .map(p -> p.getCounters(game))
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
        if (amount > 0) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, amount);
            return true;
        }
        return false;
    }
}
