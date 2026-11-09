package mage.cards.c;

import java.util.UUID;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class CommanderSpock extends CardImpl {

    public CommanderSpock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Strange new worlds -- Whenever Commander Spock enters or attacks, reveal the top card of your library. You
        // may cast that card without paying its mana cost if that spell's mana value is equal to or less than the
        // number of differently named lands you control. If it's a land card, you may put it onto the battlefield
        // tapped. If that card is still on top of your library, draw a card.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CommanderSpockEffect());
        this.addAbility(ability.addHint(StrangeNewWorldsHint.instance).setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS));
    }

    private CommanderSpock(final CommanderSpock card) {
        super(card);
    }

    @Override
    public CommanderSpock copy() {
        return new CommanderSpock(this);
    }
}

class CommanderSpockEffect extends OneShotEffect {

    CommanderSpockEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. You may cast it without paying its mana cost " +
                "if its mana value is equal or less than the number of differently named lands you control. " +
                "If it's a land card, you may put it onto the battlefield tapped. " +
                "If you don't cast it, draw a card.";
    }

    private CommanderSpockEffect(final CommanderSpockEffect effect) {
        super(effect);
    }

    @Override
    public CommanderSpockEffect copy() {
        return new CommanderSpockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isLand(game) && card.getManaValue() <= StrangeNewWorldsValue.instance.calculate(game, source, this)) {
            if (player.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + " without paying its mana cost?", source, game)) {
                boolean spellWasCast;
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                spellWasCast = player.cast(player.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                if (spellWasCast) {
                    return true;
                }
            }
        }
        if (card.isLand(game)) {
            if (player.chooseUse(Outcome.PutLandInPlay, "Put " + card.getLogName() + " onto the battlefield tapped?", source, game)) {
                if (player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null)) {
                    return true;
                }
            }
        }
        player.drawCards(1, source, game);
        return true;
    }
}