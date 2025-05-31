package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaubahnBullOfAlaMhigo extends CardImpl {


    public RaubahnBullOfAlaMhigo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward--Pay life equal to Raubahn's power.
        this.addAbility(new WardAbility(new PayLifeCost(
                SourcePermanentPowerValue.NOT_NEGATIVE, "life equal to {this}'s power"
        )));

        // Whenever Raubahn attacks, attach up to one target Equipment you control to target attacking creature.
        Ability ability = new AttacksTriggeredAbility(new AttachTargetToTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private RaubahnBullOfAlaMhigo(final RaubahnBullOfAlaMhigo card) {
        super(card);
    }

    @Override
    public RaubahnBullOfAlaMhigo copy() {
        return new RaubahnBullOfAlaMhigo(this);
    }
}
