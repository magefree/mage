package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefensiveManeuvers extends CardImpl {

    public DefensiveManeuvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Creatures of the creature type of your choice get +0/+4 until end of turn.
        this.getSpellAbility().addEffect(new DefensiveManeuversEffect());
    }

    private DefensiveManeuvers(final DefensiveManeuvers card) {
        super(card);
    }

    @Override
    public DefensiveManeuvers copy() {
        return new DefensiveManeuvers(this);
    }
}

class DefensiveManeuversEffect extends OneShotEffect {

    DefensiveManeuversEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Creatures of the creature type of your choice get +0/+4 until end of turn.";
    }

    private DefensiveManeuversEffect(final DefensiveManeuversEffect effect) {
        super(effect);
    }

    @Override
    public DefensiveManeuversEffect copy() {
        return new DefensiveManeuversEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(sourceObject);
        SubType subType = null;
        if (player.choose(outcome, choice, game)) {
            subType = SubType.byDescription(choice.getChoice());
        }
        if (subType == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(subType.getPredicate());
        game.addEffect(new BoostAllEffect(0, 4, Duration.EndOfTurn, filter, false), source);
        return true;
    }
}
