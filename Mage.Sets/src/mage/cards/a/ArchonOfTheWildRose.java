package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EnchantedBySourceControllerPredicate;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ArchonOfTheWildRose extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("other creatures you control that are enchanted by Auras you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(EnchantedBySourceControllerPredicate.instance);
    }

    public ArchonOfTheWildRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        
        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other creatures you control that are enchanted by Auras you control have base power and toughness 4/4 and have flying.
        Ability ability = new SimpleStaticAbility(
                new SetBasePowerToughnessAllEffect(4, 4, Duration.WhileOnBattlefield, filter)
        );
        ability.addEffect(new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter)
                .setText("and have flying"));
        this.addAbility(ability);
    }

    private ArchonOfTheWildRose(final ArchonOfTheWildRose card) {
        super(card);
    }

    @Override
    public ArchonOfTheWildRose copy() {
        return new ArchonOfTheWildRose(this);
    }
}
