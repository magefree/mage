package mage.cards.m;

import java.util.UUID;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class MineCollapse extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN, "a Mountain");

    public MineCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // If it's your turn, you may sacrifice a Mountain rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new SacrificeTargetCost(new TargetControlledPermanent(filter)),
                MyTurnCondition.instance,
                "If it's your turn, you may sacrifice a Mountain rather than pay this spell's mana cost."
        ).addHint(MyTurnHint.instance));

        // Mine Collapse deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private MineCollapse(final MineCollapse card) {
        super(card);
    }

    @Override
    public MineCollapse copy() {
        return new MineCollapse(this);
    }
}
