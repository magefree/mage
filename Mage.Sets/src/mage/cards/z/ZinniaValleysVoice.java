package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;

import java.util.UUID;

/**
 * @author brahle
 */
public final class ZinniaValleysVoice extends CardImpl {

    private static final FilterControlledCreaturePermanent filterBuff =
            new FilterControlledCreaturePermanent("other creatures you control with base power 1");
    private static final FilterNonlandCard filterSpells =
            new FilterNonlandCard("creature spells");

    static {
        filterBuff.add(AnotherPredicate.instance);
        filterBuff.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
        filterSpells.add(CardType.CREATURE.getPredicate());
    }

    public ZinniaValleysVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD, SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Zinnia, Valley's Voice gets +X/+0, where X is the number of other creatures you control with base power 1.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                new PermanentsOnBattlefieldCount(filterBuff), StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Creature spells you cast have offspring {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(
                new OffspringAbility("{2}"), filterSpells
        )));
    }

    private ZinniaValleysVoice(final ZinniaValleysVoice card) {
        super(card);
    }

    @Override
    public ZinniaValleysVoice copy() {
        return new ZinniaValleysVoice(this);
    }
}
