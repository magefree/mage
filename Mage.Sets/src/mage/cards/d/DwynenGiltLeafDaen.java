package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DwynenGiltLeafDaen extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELF, "attacking Elf you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public DwynenGiltLeafDaen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Other Elf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.ELF, "Elf creatures"), true)));

        // Whenever Dwynen, Gilt-Leaf Daen attacks, you gain 1 life for each attacking Elf you control.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)).setText("you gain 1 life for each attacking Elf you control"), false));
    }

    private DwynenGiltLeafDaen(final DwynenGiltLeafDaen card) {
        super(card);
    }

    @Override
    public DwynenGiltLeafDaen copy() {
        return new DwynenGiltLeafDaen(this);
    }
}
