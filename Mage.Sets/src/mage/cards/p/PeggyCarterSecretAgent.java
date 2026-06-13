package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PeggyCarterSecretAgent extends CardImpl {

    public PeggyCarterSecretAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a creature you control attacks alone, it gains indestructible until end of turn.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
            new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("it gains indestructible until end of turn"),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, false
        );
        this.addAbility(ability);

    }

    private PeggyCarterSecretAgent(final PeggyCarterSecretAgent card) {
        super(card);
    }

    @Override
    public PeggyCarterSecretAgent copy() {
        return new PeggyCarterSecretAgent(this);
    }
}
