package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SkyserpentSeeker extends CardImpl {

    public SkyserpentSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Exhaust -- {4}: Reveal cards from the top of your library until you reveal two land cards. Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order. Put a +1/+1 counter on this creature.
        Ability ability = (new ExhaustAbility(new SkyserpentSeekerEffect(), new ManaCostsImpl<>("{4}")));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), StaticValue.get(1)));
        this.addAbility(ability);
    }

    private SkyserpentSeeker(final SkyserpentSeeker card) {
        super(card);
    }

    @Override
    public SkyserpentSeeker copy() {
        return new SkyserpentSeeker(this);
    }

}
class SkyserpentSeekerEffect extends OneShotEffect {

    SkyserpentSeekerEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal two land cards. " +
                "Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order";
    }

    private SkyserpentSeekerEffect(final SkyserpentSeekerEffect effect) {
        super(effect);
    }

    @Override
    public SkyserpentSeekerEffect copy() {
        return new SkyserpentSeekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards revealedCards = new CardsImpl();
        Cards lands = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isLand()) {
                lands.add(card);
                if (lands.size() == 2) {
                    break;
                }
            } else {
                revealedCards.add(card);
            }
        }
        controller.revealCards(source, revealedCards, game);
        PutCards.BATTLEFIELD_TAPPED.moveCards(controller, lands, source, game);
        PutCards.BOTTOM_RANDOM.moveCards(controller, revealedCards, source, game);
        return true;
    }
}
