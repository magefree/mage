package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.EnchantmentSourcePredicate;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaverOfHarmony extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("enchantment creatures");
    private static final FilterStackObject filter2
            = new FilterStackObject("activated or triggered ability you control from an enchantment source");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter2.add(EnchantmentSourcePredicate.instance);
    }

    public WeaverOfHarmony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other enchantment creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {G}, {T}: Copy target activated or triggered ability you control from an enchantment source. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackAbilityEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter2));
        this.addAbility(ability);
    }

    private WeaverOfHarmony(final WeaverOfHarmony card) {
        super(card);
    }

    @Override
    public WeaverOfHarmony copy() {
        return new WeaverOfHarmony(this);
    }
}
