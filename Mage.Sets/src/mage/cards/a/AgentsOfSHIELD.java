package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AgentsOfSHIELD extends CardImpl {

    public AgentsOfSHIELD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
            new BoostTargetEffect(1, 1).setText("that creature gets +1/+1 until end of turn"),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, false
        );
        this.addAbility(ability);
    }

    private AgentsOfSHIELD(final AgentsOfSHIELD card) {
        super(card);
    }

    @Override
    public AgentsOfSHIELD copy() {
        return new AgentsOfSHIELD(this);
    }
}
