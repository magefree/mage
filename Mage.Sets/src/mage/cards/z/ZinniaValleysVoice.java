package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import java.util.UUID;

import static mage.abilities.dynamicvalue.common.StaticValue.get;
import static mage.constants.Duration.WhileOnBattlefield;


/**
 * Zinnia, Valley's Voice
 *
 * Legendary Creature — Bird Bard
 *
 * Flying
 * Zinnia, Valley's Voice gets +X/+0, where X is the number of other creatures
 * you control with base power 1.
 * Creature spells you cast have offspring {2}.
 *
 * @author DreamWaker      and sneddigrolyat
 */
public final class ZinniaValleysVoice extends CardImpl {

    // "other creatures you control with base power 1"
    private static final FilterCreaturePermanent bfilter = new FilterCreaturePermanent("other creatures you control with base power 1");

    static {
        bfilter.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
        bfilter.add(TargetController.YOU.getControllerPredicate());
        bfilter.add(AnotherPredicate.instance);
    }

    private static final PermanentsOnBattlefieldCount bcount = new PermanentsOnBattlefieldCount(bfilter);

    // "creature spells you cast"
    static final FilterNonlandCard cfilter = new FilterNonlandCard("creature spells you cast");

    static {
        cfilter.add(CardType.CREATURE.getPredicate());
    }


    public ZinniaValleysVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creature spells you cast have offspring {2}.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new OffspringAbility("{2}"), cfilter)
                        .setText("Creature spells you cast have offspring {2}.")
        ));

        // Zinnia, Valley's Voice gets +X/+0,
        // where X is the number of other creatures you control with base power 1.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(bcount, get(0), WhileOnBattlefield)
        ));
    }

    private ZinniaValleysVoice(final ZinniaValleysVoice card) {
        super(card);
    }

    @Override
    public ZinniaValleysVoice copy() {
        return new ZinniaValleysVoice(this);
    }
}

