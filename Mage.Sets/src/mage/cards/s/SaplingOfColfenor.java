
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class SaplingOfColfenor extends CardImpl {

    public SaplingOfColfenor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B/G}{B/G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Sapling of Colfenor is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Sapling of Colfenor attacks, reveal the top card of your library. If it's a creature card, you gain life equal to that card's toughness, lose life equal to its power, then put it into your hand.
        this.addAbility(new AttacksTriggeredAbility(new SaplingOfColfenorEffect(), false));

    }

    private SaplingOfColfenor(final SaplingOfColfenor card) {
        super(card);
    }

    @Override
    public SaplingOfColfenor copy() {
        return new SaplingOfColfenor(this);
    }
}

class SaplingOfColfenorEffect extends OneShotEffect {

    public SaplingOfColfenorEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. If it's a creature card, you gain life equal to that card's toughness, lose life equal to its power, then put it into your hand";
    }

    public SaplingOfColfenorEffect(final SaplingOfColfenorEffect effect) {
        super(effect);
    }

    @Override
    public SaplingOfColfenorEffect copy() {
        return new SaplingOfColfenorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if(card != null) {
                    Cards cards = new CardsImpl(card);
                    controller.revealCards(sourceObject.getIdName(), cards, game);
                    if (card.isCreature(game)) {
                        controller.gainLife(card.getToughness().getValue(), game, source);
                        controller.loseLife(card.getPower().getValue(), game, source, false);
                        return controller.moveCards(cards.getCards(game), Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
