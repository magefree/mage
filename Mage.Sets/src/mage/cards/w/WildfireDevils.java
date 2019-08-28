package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
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
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import org.apache.log4j.Logger;

import java.util.UUID;

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
        super(Outcome.Benefit);
        staticText = "choose a player at random. That player exiles an instant or sorcery card from their graveyard. " +
                "Copy that card. You may cast the copy without paying its mana cost.";
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
        TargetPlayer targetPlayer = new TargetPlayer();
        targetPlayer.setRandom(true);
        targetPlayer.setNotTarget(true);
        if (!controller.choose(outcome, targetPlayer, source.getSourceId(), game)) {
            return false;
        }
        Player player = game.getPlayer(targetPlayer.getFirstTarget());
        if (player == null || player.getGraveyard().getCards(game).stream().noneMatch(Card::isInstantOrSorcery)) {
            return false;
        }
        TargetCard targetCard = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        targetCard.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), targetCard, game)) {
            return false;
        }
        Card card = game.getCard(targetCard.getFirstTarget());
        player.moveCards(card, Zone.EXILED, source, game);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, controller.getId());
        if (copiedCard == null) {
            return false;
        }
        game.getExile().add(source.getSourceId(), "", card);
        game.setZone(copiedCard.getId(), Zone.EXILED);
        if (!controller.chooseUse(outcome, "Cast the exiled card?", source, game)) {
            return true;
        }
        if (copiedCard.getSpellAbility() == null) {
            Logger.getLogger(WildfireDevilsEffect.class).error("Wildfire Devils: spell ability == null " + copiedCard.getName());
            return true;
        }
        return controller.cast(copiedCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
    }
}