package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AndTheyShallKnowNoFear extends CardImpl {

    public AndTheyShallKnowNoFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose a creature type. Creatures you control of the chosen type get +1/+0 and gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new AndTheyShallKnowNoFearEffect());
    }

    private AndTheyShallKnowNoFear(final AndTheyShallKnowNoFear card) {
        super(card);
    }

    @Override
    public AndTheyShallKnowNoFear copy() {
        return new AndTheyShallKnowNoFear(this);
    }
}

class AndTheyShallKnowNoFearEffect extends OneShotEffect {

    AndTheyShallKnowNoFearEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Creatures you control of the chosen type " +
                "get +1/+0 and gain indestructible until end of turn";
    }

    private AndTheyShallKnowNoFearEffect(final AndTheyShallKnowNoFearEffect effect) {
        super(effect);
    }

    @Override
    public AndTheyShallKnowNoFearEffect copy() {
        return new AndTheyShallKnowNoFearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Choice choice = new ChoiceCreatureType(sourceObject);
        player.choose(outcome, choice, game);
        SubType subType = SubType.fromString(choice.getChoice());
        if (subType == null) {
            return false;
        }
        game.informPlayers(player.getLogName() + " chooses " + subType);
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .filter(permanent -> permanent.hasSubtype(subType, game))
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
