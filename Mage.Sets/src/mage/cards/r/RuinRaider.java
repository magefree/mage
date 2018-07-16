
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class RuinRaider extends CardImpl {

    public RuinRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i> &mdash; At the beginning of your end step, if you attacked with a creature this turn, reveal the top card of your library and put that card into your hand. You lose life equal to the card's converted mana cost.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new RuinRaiderEffect(), TargetController.YOU, false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; At the beginning of your end step, if you attacked with a creature this turn, "
                + "reveal the top card of your library and put that card into your hand. "
                + "You lose life equal to the card's converted mana cost.");
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public RuinRaider(final RuinRaider card) {
        super(card);
    }

    @Override
    public RuinRaider copy() {
        return new RuinRaider(this);
    }
}

class RuinRaiderEffect extends OneShotEffect {

    RuinRaiderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost";
    }

    RuinRaiderEffect(final RuinRaiderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    controller.revealCards(source, cards, game);
                    controller.moveCards(card, Zone.HAND, source, game);
                    controller.loseLife(card.getConvertedManaCost(), game, false);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RuinRaiderEffect copy() {
        return new RuinRaiderEffect(this);
    }
}
