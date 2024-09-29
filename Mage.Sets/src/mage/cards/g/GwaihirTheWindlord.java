package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GwaihirTheWindlord extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BIRD, "Birds");

    public GwaihirTheWindlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {2} less to cast as long as you've drawn two or more cards this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, DrewTwoOrMoreCardsCondition.instance)
                .setText("this spell costs {2} less to cast as long as you've drawn two or more cards this turn")
        ).setRuleAtTheTop(true).addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Other Birds you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private GwaihirTheWindlord(final GwaihirTheWindlord card) {
        super(card);
    }

    @Override
    public GwaihirTheWindlord copy() {
        return new GwaihirTheWindlord(this);
    }
}
