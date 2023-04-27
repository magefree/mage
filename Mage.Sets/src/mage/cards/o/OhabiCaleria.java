package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OhabiCaleria extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.ARCHER, "Archers you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.ARCHER, "an Archer you control");

    public OhabiCaleria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Untap all Archers you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapAllDuringEachOtherPlayersUntapStepEffect(filter)));

        // Whenever an Archer you control deals damage to a creature, you may pay {2}. If you do, draw a card.
        this.addAbility(new DealsDamageToACreatureAllTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        ), false, filter2, SetTargetPointer.NONE, false));
    }

    private OhabiCaleria(final OhabiCaleria card) {
        super(card);
    }

    @Override
    public OhabiCaleria copy() {
        return new OhabiCaleria(this);
    }
}
