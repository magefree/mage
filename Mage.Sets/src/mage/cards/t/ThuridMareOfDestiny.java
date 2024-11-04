package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThuridMareOfDestiny extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Pegasus, Unicorn, or Horse creature spell");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Pegasi, Unicorns, and Horses");
    private static final Predicate<MageObject> predicate = Predicates.or(
            SubType.PEGASUS.getPredicate(),
            SubType.UNICORN.getPredicate(),
            SubType.HORSE.getPredicate()
    );

    static {
        filter.add(predicate);
        filter2.add(predicate);
    }

    public ThuridMareOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast a Pegasus, Unicorn, or Horse creature spell, copy it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetStackObjectEffect(false, true, false)
                        .withText("it"),
                filter, false, SetTargetPointer.SPELL
        ));

        // Other Pegasi, Unicorns, and Horses you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private ThuridMareOfDestiny(final ThuridMareOfDestiny card) {
        super(card);
    }

    @Override
    public ThuridMareOfDestiny copy() {
        return new ThuridMareOfDestiny(this);
    }
}
