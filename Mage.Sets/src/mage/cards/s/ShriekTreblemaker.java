package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ShriekTreblemaker extends CardImpl {

    public ShriekTreblemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your first main phase, you may discard a card. When you do, target creature can't block this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DoWhenCostPaid(ability, new DiscardCardCost(),
                "Discard a card to make target creature unable to block this turn?"))
        );

        // Sonic Blast -- Whenever a creature an opponent controls dies, Shriek deals 1 damage to that player.
        this.addAbility(new DiesCreatureTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1, true, "that player"),
                false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE,
                SetTargetPointer.PLAYER)
                .withFlavorWord("Sonic Blast")
        );
    }

    private ShriekTreblemaker(final ShriekTreblemaker card) {
        super(card);
    }

    @Override
    public ShriekTreblemaker copy() {
        return new ShriekTreblemaker(this);
    }
}
