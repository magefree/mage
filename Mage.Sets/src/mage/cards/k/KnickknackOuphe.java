package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Xanderhall
 */
public final class KnickknackOuphe extends CardImpl {

    public KnickknackOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");
        
        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Knickknack Ouphe enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Knickknack Ouphe enters the battlefield, reveal the top X cards of your library. You may put any number of Aura cards with mana value X or less from among them onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KnickknackOuphePutOntoBattlefieldEffect()));

    }

    private KnickknackOuphe(final KnickknackOuphe card) {
        super(card);
    }

    @Override
    public KnickknackOuphe copy() {
        return new KnickknackOuphe(this);
    }
}

class KnickknackOuphePutOntoBattlefieldEffect extends OneShotEffect {

    public KnickknackOuphePutOntoBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal the top X cards of your library. " + 
        "You may put any number of Aura cards with mana value X or less from among them onto the battlefield. " + 
        "Then put all cards revealed this way that weren't put onto the battlefield on the bottom of your library in a random order";
    }

    private KnickknackOuphePutOntoBattlefieldEffect(final KnickknackOuphePutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = ManacostVariableValue.ETB.calculate(game, source, null);
            if (count > 0) {
                Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, count));
                controller.revealCards(source, cards, game);

                FilterCard filter = new FilterEnchantmentCard("Aura cards with mana value " + count + " or less to put onto the battlefield");
                filter.add(SubType.AURA.getPredicate());
                filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, count));
                
                if (cards.count(filter, controller.getId(), source, game) > 0) {
                    TargetCard targetAuras = new TargetCard(0, count, Zone.LIBRARY, filter);
                    targetAuras.setRequired(false);

                    if (controller.choose(Outcome.PutCardInPlay, cards, targetAuras, source, game)) {
                        targetAuras.getTargets().stream().forEach(t -> {
                            Card card = cards.get(t, game);
                            if (card != null) {
                                cards.remove(card);
                                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            }
                        });

                        targetAuras.clearChosen();
                    } else {
                        game.informPlayers(controller.getLogName() + " didn't choose anything");
                    }
                } else {
                    game.informPlayers("No Aura cards with mana value " + count + " or less to choose.");
                }

                if (!cards.isEmpty()) {
                    PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public KnickknackOuphePutOntoBattlefieldEffect copy() {
        return new KnickknackOuphePutOntoBattlefieldEffect(this);
    }

}
