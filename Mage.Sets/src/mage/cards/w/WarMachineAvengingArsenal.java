package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.ModifiedPredicate;

/**
 *
 * @author Grath
 */
public final class WarMachineAvengingArsenal extends CardImpl {

    private static final FilterAttackingCreature filter =
            new FilterAttackingCreature("attacking modified creatures you control");

    static {
        filter.add(ModifiedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public WarMachineAvengingArsenal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever War Machine attacks, attacking modified creatures you control gain double strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter)
        ));
    }

    private WarMachineAvengingArsenal(final WarMachineAvengingArsenal card) {
        super(card);
    }

    @Override
    public WarMachineAvengingArsenal copy() {
        return new WarMachineAvengingArsenal(this);
    }
}
