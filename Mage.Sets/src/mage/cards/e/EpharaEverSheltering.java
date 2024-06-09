package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EpharaEverSheltering extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent("another enchantment");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.MORE_THAN, 2, true
    );
    private static final Hint hint = new ValueHint(
            "Other enchantments you control", new PermanentsOnBattlefieldCount(filter)
    );

    public EpharaEverSheltering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Ephara, Ever-Sheltering has lifelink and indestructible as long as you control at least three other enchantments.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                condition, "{this} has lifelink"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance()), condition,
                "and indestructible as long as you control at least three other enchantments"
        ));
        this.addAbility(ability.addHint(hint));

        // Whenever another enchantment enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private EpharaEverSheltering(final EpharaEverSheltering card) {
        super(card);
    }

    @Override
    public EpharaEverSheltering copy() {
        return new EpharaEverSheltering(this);
    }
}
