package mage.cards.m;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public final class MindleechMass extends CardImpl {

    public MindleechMass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Mindleech Mass deals combat damage to a player, you may look at that 
        // player's hand. If you do, you may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MindleechMassEffect(), true, true));
    }

    public MindleechMass(final MindleechMass card) {
        super(card);
    }

    @Override
    public MindleechMass copy() {
        return new MindleechMass(this);
    }
}

class MindleechMassEffect extends OneShotEffect {

    public MindleechMassEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may look at that player's hand. If you do, "
                + "you may cast a nonland card in it without paying that card's mana cost";
    }

    public MindleechMassEffect(final MindleechMassEffect effect) {
        super(effect);
    }

    @Override
    public MindleechMassEffect copy() {
        return new MindleechMassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(opponent.getHand());
            opponent.revealCards("Opponents hand", cardsInHand, game);
            if (!cardsInHand.isEmpty()
                    && !cardsInHand.getCards(new FilterNonlandCard(), game).isEmpty()) {
                TargetCard target = new TargetCard(1, Zone.HAND, new FilterNonlandCard());
                if (controller.chooseTarget(Outcome.PlayForFree, cardsInHand, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        controller.cast(controller.chooseAbilityForCast(card, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
