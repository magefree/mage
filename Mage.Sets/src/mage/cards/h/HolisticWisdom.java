
package mage.cards.h;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class HolisticWisdom extends CardImpl {

    public HolisticWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");

        // {2}, Exile a card from your hand: Return target card from your graveyard to your hand if it shares a card type with the card exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HolisticWisdomEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(new FilterCard("a card from your hand"))));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private HolisticWisdom(final HolisticWisdom card) {
        super(card);
    }

    @Override
    public HolisticWisdom copy() {
        return new HolisticWisdom(this);
    }
}

class HolisticWisdomEffect extends OneShotEffect {

    public HolisticWisdomEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target card from your graveyard to your hand if it shares a card type with the card exiled this way";
    }

    public HolisticWisdomEffect(final HolisticWisdomEffect effect) {
        super(effect);
    }

    @Override
    public HolisticWisdomEffect copy() {
        return new HolisticWisdomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileFromHandCost) {
                    List<CardType> cardtypes = new ArrayList<>();
                    ExileFromHandCost exileCost = (ExileFromHandCost) cost;
                    for (CardType cardtype : exileCost.getCards().get(0).getCardType(game)) {
                        cardtypes.add(cardtype);
                    }
                    for (CardType cardtype : card.getCardType(game)) {
                        if (cardtypes.contains(cardtype)) {
                            Effect effect = new ReturnToHandTargetEffect();
                            effect.setTargetPointer(new FixedTarget(card.getId(), game));
                            return effect.apply(game, source);
                        }
                    }
                }
            }
        }
        return false;
    }
}
