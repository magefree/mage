package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
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
public final class SickeningShoal extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a black card with mana value X from your hand");
    private static final DynamicValue xValue = new SignInversionDynamicValue(ExileFromHandCostCardConvertedMana.instance);

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SickeningShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a black card with converted mana cost X from your hand rather than pay Sickening Shoal's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(
                new TargetCardInHand(filter), true
        )));

        // Target creature gets -X/-X until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SickeningShoal(final SickeningShoal card) {
        super(card);
    }

    @Override
    public SickeningShoal copy() {
        return new SickeningShoal(this);
    }
}
