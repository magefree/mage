package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class CouncilOfReeds extends CardImpl {

    public CouncilOfReeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // The "legend rule" doesn't apply to creatures you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(StaticFilters.FILTER_CONTROLLED_CREATURES)));

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, create a token that's a copy of Council of Reeds.
        Ability ability = new BeginningOfCombatTriggeredAbility(new CreateTokenCopySourceEffect())
            .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance);
        this.addAbility(ability);
    }

    private CouncilOfReeds(final CouncilOfReeds card) {
        super(card);
    }

    @Override
    public CouncilOfReeds copy() {
        return new CouncilOfReeds(this);
    }
}
