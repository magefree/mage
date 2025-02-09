package mage.cards.v;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViolentUrge extends CardImpl {

    public ViolentUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +1/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setText("Target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Delirium -- If there are four or more card types among cards in your graveyard, that creature gains double strike until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())),
                DeliriumCondition.instance, AbilityWord.DELIRIUM.formatWord() + "If there are four or more " +
                "card types among cards in your graveyard, that creature gains double strike until end of turn"
        ).concatBy("<br>"));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private ViolentUrge(final ViolentUrge card) {
        super(card);
    }

    @Override
    public ViolentUrge copy() {
        return new ViolentUrge(this);
    }
}
