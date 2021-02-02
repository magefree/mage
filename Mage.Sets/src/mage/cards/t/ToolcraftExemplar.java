
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class ToolcraftExemplar extends CardImpl {

    public ToolcraftExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control an artifact, Toolcraft Exemplar gets +2/+1 until end of turn.
        // If you control at least 3 artifacts, it also gains first strike until end of turn.
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent(), ComparisonType.MORE_THAN, 2)), null);
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new BoostSourceEffect(2, 1, Duration.EndOfTurn), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent()),
                "At the beginning of combat on your turn, if you control an artifact, {this} gets +2/+1 until end of turn."
                        + " If you control at least 3 artifacts, it also gains first strike until end of turn.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ToolcraftExemplar(final ToolcraftExemplar card) {
        super(card);
    }

    @Override
    public ToolcraftExemplar copy() {
        return new ToolcraftExemplar(this);
    }
}
