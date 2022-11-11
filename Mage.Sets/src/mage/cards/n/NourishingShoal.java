package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NourishingShoal extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a green card with mana value X from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NourishingShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{G}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(
                new TargetCardInHand(filter), true
        )));

        // You gain X life.
        this.getSpellAbility().addEffect(new GainLifeEffect(ExileFromHandCostCardConvertedMana.instance));
    }

    private NourishingShoal(final NourishingShoal card) {
        super(card);
    }

    @Override
    public NourishingShoal copy() {
        return new NourishingShoal(this);
    }
}
