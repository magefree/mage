package mage.cards.w;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WondrousCrucible extends CardImpl {

    public WondrousCrucible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // Permanents you control have ward {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(2), false), Duration.WhileOnBattlefield
        )));

        // At the beginning of your end step, mill two cards, then exile a nonland card at random from your graveyard. Copy it. You may cast the copy without paying its mana cost.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new WondrousCrucibleEffect(), TargetController.YOU, false
        ));
    }

    private WondrousCrucible(final WondrousCrucible card) {
        super(card);
    }

    @Override
    public WondrousCrucible copy() {
        return new WondrousCrucible(this);
    }
}

class WondrousCrucibleEffect extends OneShotEffect {

    WondrousCrucibleEffect() {
        super(Outcome.Benefit);
        staticText = "mill two cards, then exile a nonland card at random from your graveyard. " +
                "Copy it. You may cast the copy without paying its mana cost";
    }

    private WondrousCrucibleEffect(final WondrousCrucibleEffect effect) {
        super(effect);
    }

    @Override
    public WondrousCrucibleEffect copy() {
        return new WondrousCrucibleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(2, source, game);
        Card card = RandomUtil.randomFromCollection(
                player.getGraveyard().getCards(StaticFilters.FILTER_CARD_NON_LAND, game)
        );
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, player.getId());
        if (!player.chooseUse(outcome, "Cast copy of " + card.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
