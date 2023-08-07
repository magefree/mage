package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import java.util.UUID;
import mage.ApprovingObject;
import mage.players.PlayerList;
import mage.target.common.TargetCardInGraveyard;
import mage.util.RandomUtil;

/**
 * @author TheElk801
 */
public final class WildfireDevils extends CardImpl {

    public WildfireDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Wildfire Devils enters the battlefield and at the beginning of your upkeep, choose a player at random. That player exiles an instant or sorcery card from their graveyard. Copy that card. You may cast the copy without paying its mana cost.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new WildfireDevilsEffect(), false,
                "When {this} enters the battlefield and at the beginning of your upkeep, ",
                new EntersBattlefieldTriggeredAbility(null, false),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false)
        ));
    }

    private WildfireDevils(final WildfireDevils card) {
        super(card);
    }

    @Override
    public WildfireDevils copy() {
        return new WildfireDevils(this);
    }
}

class WildfireDevilsEffect extends OneShotEffect {

    WildfireDevilsEffect() {
        super(Outcome.Neutral);
        staticText = "choose a player at random. That player exiles an instant or sorcery card from their graveyard. "
                + "Copy that card. You may cast the copy without paying its mana cost.";
    }

    private WildfireDevilsEffect(final WildfireDevilsEffect effect) {
        super(effect);
    }

    @Override
    public WildfireDevilsEffect copy() {
        return new WildfireDevilsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        PlayerList players = game.getState().getPlayersInRange(controller.getId(), game);
        if (players == null) {
            return false;
        }
        Player randomPlayer = game.getPlayer(players.get(RandomUtil.nextInt(players.size())));
        if (randomPlayer == null) {
            return false;
        }
        game.informPlayers("The chosen random player is " + randomPlayer.getLogName());
        if (randomPlayer.getGraveyard().getCards(game).stream().noneMatch(card1 -> card1.isInstantOrSorcery(game))) {
            return false;
        }
        TargetCardInGraveyard targetCard = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        targetCard.setNotTarget(true);
        if (!randomPlayer.choose(Outcome.Discard, randomPlayer.getGraveyard(), targetCard, source, game)) {
            return false;
        }
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        randomPlayer.moveCards(card, Zone.EXILED, source, game);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, controller.getId());
        if (copiedCard == null) {
            return false;
        }
        if (!controller.chooseUse(outcome, "Cast the copy of the exiled card?", source, game)) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(copiedCard, game, true), game, true,
                new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return cardWasCast;
    }
}
