package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ToolcraftExemplar extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control an artifact")
    );

    public ToolcraftExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control an artifact, Toolcraft Exemplar gets +2/+1 until end of turn.
        // If you control at least 3 artifacts, it also gains first strike until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostSourceEffect(
                2, 1, Duration.EndOfTurn
        )).withInterveningIf(condition);
        ability.addEffect(new ConditionalOneShotEffect(new AddContinuousEffectToGame(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
        ), MetalcraftCondition.instance, "If you control three or more artifacts, it also gains first strike until end of turn"));
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private ToolcraftExemplar(final ToolcraftExemplar card) {
        super(card);
    }

    @Override
    public ToolcraftExemplar copy() {
        return new ToolcraftExemplar(this);
    }
}
