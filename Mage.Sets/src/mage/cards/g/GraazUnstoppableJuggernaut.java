package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author TheElk801
 */
public final class GraazUnstoppableJuggernaut extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.JUGGERNAUT, "Juggernauts you control");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent(SubType.WALL, "Walls");
    private static final FilterPermanent filter3
            = new FilterControlledCreaturePermanent("other creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter3.add(AnotherPredicate.instance);
    }

    public GraazUnstoppableJuggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Juggernauts you control attack each combat if able.
        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(filter)));

        // Juggernauts you control can't be blocked by Walls.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesAllEffect(filter, filter2, Duration.WhileOnBattlefield)
        ));

        // Other creatures you control have base power and toughness 5/3 and are Juggernauts in addition to their other creature types.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessAllEffect(
                5, 3, Duration.WhileOnBattlefield, filter3, true
        ));
        ability.addEffect(new AddCardSubtypeAllEffect(
                filter3, SubType.JUGGERNAUT, null
        ).setText("and are Juggernauts in addition to their other creature types"));
        this.addAbility(ability);
    }

    private GraazUnstoppableJuggernaut(final GraazUnstoppableJuggernaut card) {
        super(card);
    }

    @Override
    public GraazUnstoppableJuggernaut copy() {
        return new GraazUnstoppableJuggernaut(this);
    }
}
