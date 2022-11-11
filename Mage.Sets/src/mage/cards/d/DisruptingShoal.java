package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DisruptingShoal extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a blue card with mana value X from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DisruptingShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(
                new TargetCardInHand(filter), true
        )));

        // 2/1/2005: Disrupting Shoal can target any spell, but does nothing unless that spell's converted mana cost is X.
        // Counter target spell if its converted mana cost is X.
        this.getSpellAbility().addEffect(new DisruptingShoalCounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DisruptingShoal(final DisruptingShoal card) {
        super(card);
    }

    @Override
    public DisruptingShoal copy() {
        return new DisruptingShoal(this);
    }
}

class DisruptingShoalCounterTargetEffect extends OneShotEffect {

    public DisruptingShoalCounterTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell if its mana value is X";
    }

    public DisruptingShoalCounterTargetEffect(final DisruptingShoalCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public DisruptingShoalCounterTargetEffect copy() {
        return new DisruptingShoalCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null && isManaValueEqual(source, spell.getManaValue())) {
            return game.getStack().counter(source.getFirstTarget(), source, game);
        }
        return false;
    }

    private boolean isManaValueEqual(Ability sourceAbility, int amount) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost.isPaid() && cost instanceof ExileFromHandCost) {
                for (Card card : ((ExileFromHandCost) cost).getCards()) {
                    return card.getManaValue() == amount;
                }
                return false;
            }
        }
        // No alternate costs payed so compare to X value
        return sourceAbility.getManaCostsToPay().getX() == amount;
    }
}
