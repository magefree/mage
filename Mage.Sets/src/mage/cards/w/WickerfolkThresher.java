package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WickerfolkThresher extends CardImpl {

    public WickerfolkThresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Delirium -- Whenever Wickerfolk Thresher attacks, if there are four or more card types among cards in your graveyard, look at the top card of your library. If it's a land card, you may put it onto the battlefield. If you don't put the card onto the battlefield, put it into your hand.
        this.addAbility(new AttacksTriggeredAbility(new WickerfolkThresherEffect())
                .withInterveningIf(DeliriumCondition.instance)
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private WickerfolkThresher(final WickerfolkThresher card) {
        super(card);
    }

    @Override
    public WickerfolkThresher copy() {
        return new WickerfolkThresher(this);
    }
}

class WickerfolkThresherEffect extends OneShotEffect {

    WickerfolkThresherEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield. " +
                "If you don't put the card onto the battlefield, put it into your hand";
    }

    private WickerfolkThresherEffect(final WickerfolkThresherEffect effect) {
        super(effect);
    }

    @Override
    public WickerfolkThresherEffect copy() {
        return new WickerfolkThresherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        if (card.isLand(game) && player.chooseUse(Outcome.PutLandInPlay, "Put it onto the battlefield?", source, game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (!Zone.BATTLEFIELD.match(game.getState().getZone(card.getId()))) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
