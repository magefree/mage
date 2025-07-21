package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class PalazzoArchers extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public PalazzoArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever a creature with flying attacks you or a planeswalker you control, Palazzo Archers deals damage equal to its power to that creature.
        this.addAbility(new AttacksAllTriggeredAbility(new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE).withTargetDescription("that creature"),
                false, filter, SetTargetPointer.PERMANENT, true));
    }

    private PalazzoArchers(final PalazzoArchers card) {
        super(card);
    }

    @Override
    public PalazzoArchers copy() {
        return new PalazzoArchers(this);
    }
}
