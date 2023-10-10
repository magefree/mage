
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class SifterWurm extends CardImpl {

    public SifterWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Sifter Wurm enters the battlefield, scry 3, then reveal the top card of your library. You gain life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SifterWurmEffect());
        this.addAbility(ability);
    }

    private SifterWurm(final SifterWurm card) {
        super(card);
    }

    @Override
    public SifterWurm copy() {
        return new SifterWurm(this);
    }
}

class SifterWurmEffect extends OneShotEffect {

    public SifterWurmEffect() {
        super(Outcome.DrawCard);
        this.staticText = "scry 3, then reveal the top card of your library. You gain life equal to that card's mana value.";
    }

    private SifterWurmEffect(final SifterWurmEffect effect) {
        super(effect);
    }

    @Override
    public SifterWurmEffect copy() {
        return new SifterWurmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            controller.scry(3, source, game);

            Card card = controller.getLibrary().getFromTop(game);

            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                controller.gainLife(card.getManaValue(), game, source);
            }
            return true;
        }
        return false;
    }
}
