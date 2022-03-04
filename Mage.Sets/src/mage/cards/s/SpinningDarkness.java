package mage.cards.s;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class SpinningDarkness extends CardImpl {

    public SpinningDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}{B}");

        // You may exile the top three black cards of your graveyard rather than pay Spinning Darkness's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SpinningDarknessCost()));
        
        // Spinning Darkness deals 3 damage to target nonblack creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private SpinningDarkness(final SpinningDarkness card) {
        super(card);
    }

    @Override
    public SpinningDarkness copy() {
        return new SpinningDarkness(this);
    }
}

class SpinningDarknessCost extends CostImpl {

    private static final FilterCard filter = new FilterCard("black card");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    SpinningDarknessCost() {
        this.text = "exile the top three black cards of your graveyard";      
    }

    SpinningDarknessCost(final SpinningDarknessCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Set<Card> blackCardsInGraveyard = controller.getGraveyard().getCards(filter, game);
            int size = blackCardsInGraveyard.size();
            if (size >= 3) {
                Iterator<Card> it = blackCardsInGraveyard.iterator();
                Cards cardsToExile = new CardsImpl();
                int i = 1;
                while (cardsToExile.size() < 3) {
                    Card card = it.next();
                    if (i > size - 3) {
                        cardsToExile.add(card);
                    }
                    i++;
                }
                paid = controller.moveCards(cardsToExile, Zone.EXILED, ability, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getGraveyard().getCards(filter, game).size() >= 3;
        }
        return false;
    }

    @Override
    public SpinningDarknessCost copy() {
        return new SpinningDarknessCost(this);
    }
}
