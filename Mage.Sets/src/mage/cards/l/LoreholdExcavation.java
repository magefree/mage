package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Spirit32Token;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdExcavation extends CardImpl {

    public LoreholdExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        // At the beginning of your end step, mill a card. If a land card was milled this way, you gain 1 life. Otherwise, Lorehold Excavation deals 1 damage to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoreholdExcavationEffect(), TargetController.YOU, false
        ));

        // {5}, Exile a creature card from your graveyard: Create a tapped 3/2 red and white Spirit creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(
                new Spirit32Token(), 1, true, false
        ), new GenericManaCost(5));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)));
        this.addAbility(ability);
    }

    private LoreholdExcavation(final LoreholdExcavation card) {
        super(card);
    }

    @Override
    public LoreholdExcavation copy() {
        return new LoreholdExcavation(this);
    }
}

class LoreholdExcavationEffect extends OneShotEffect {

    private static final Effect effect = new DamagePlayersEffect(1, TargetController.OPPONENT);

    LoreholdExcavationEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. If a land card was milled this way, you gain 1 life. " +
                "Otherwise, {this} deals 1 damage to each opponent";
    }

    private LoreholdExcavationEffect(final LoreholdExcavationEffect effect) {
        super(effect);
    }

    @Override
    public LoreholdExcavationEffect copy() {
        return new LoreholdExcavationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.millCards(1, source, game).count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
            player.gainLife(1, game, source);
            return true;
        }
        effect.apply(game, source);
        return true;
    }
}
