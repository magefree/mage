package mage.cards.b;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlazingShoal extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a red card with mana value X from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public BlazingShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a red card with converted mana cost X from your hand rather than pay Blazing Shoal's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(
                new TargetCardInHand(filter), true
        )));

        // Target creature gets +X/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ExileFromHandCostCardConvertedMana.instance,
                StaticValue.get(0), Duration.EndOfTurn
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BlazingShoal(final BlazingShoal card) {
        super(card);
    }

    @Override
    public BlazingShoal copy() {
        return new BlazingShoal(this);
    }
}
