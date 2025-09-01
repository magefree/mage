package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckingBallArm extends CardImpl {

    public WreckingBallArm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has base power and toughness 7/7 and can't be blocked by creatures with power 2 or less.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessAttachedEffect(
                7, 7, AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new CantBeBlockedByCreaturesAttachedEffect(
                Duration.WhileControlled, DauntAbility.getFilter(), AttachmentType.EQUIPMENT
        ).setText("and can't be blocked by creatures with power 2 or less"));
        this.addAbility(ability);

        // Equip legendary creature {3}
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature, new GenericManaCost(3),
                new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY)
        ));

        // Equip {7}
        this.addAbility(new EquipAbility(7));
    }

    private WreckingBallArm(final WreckingBallArm card) {
        super(card);
    }

    @Override
    public WreckingBallArm copy() {
        return new WreckingBallArm(this);
    }
}
