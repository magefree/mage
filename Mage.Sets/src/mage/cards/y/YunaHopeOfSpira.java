package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YunaHopeOfSpira extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    private static final FilterPermanent filter2 = new FilterPermanentThisOrAnother(filter, false);

    private static final FilterCard filter3 = new FilterEnchantmentCard("enchantment card from your graveyard");

    public YunaHopeOfSpira(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // During your turn, Yuna and enchantment creatures you control have trample, lifelink, and ward {2}.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter2),
                MyTurnCondition.instance, "during your turn, {this} and enchantment creatures you control have trample"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ), MyTurnCondition.instance, ", lifelink"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAllEffect(
                new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield, filter2
        ), MyTurnCondition.instance, ", and ward {2}"));
        this.addAbility(ability);

        // At the beginning of your end step, return up to one target enchantment card from your graveyard to the battlefield with a finality counter on it.
        ability = new BeginningOfEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance())
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter3));
        this.addAbility(ability);
    }

    private YunaHopeOfSpira(final YunaHopeOfSpira card) {
        super(card);
    }

    @Override
    public YunaHopeOfSpira copy() {
        return new YunaHopeOfSpira(this);
    }
}
