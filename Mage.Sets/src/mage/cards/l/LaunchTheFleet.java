package mage.cards.l;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LaunchTheFleet extends CardImpl {

    public LaunchTheFleet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Strive - Launch the Fleet costs 1 more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}"));

        // Until end of turn, any number of target creatures each gain "Whenever this creature attacks, create a 1/1 white Soldier token tapped and attacking."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        Effect effect = new GainAbilityTargetEffect(new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 1, true, true), false), Duration.EndOfTurn);
        effect.setText("Until end of turn, any number of target creatures each gain \"Whenever this creature attacks, create a 1/1 white Soldier creature token that's tapped and attacking.\"");
        this.getSpellAbility().addEffect(effect);

    }

    private LaunchTheFleet(final LaunchTheFleet card) {
        super(card);
    }

    @Override
    public LaunchTheFleet copy() {
        return new LaunchTheFleet(this);
    }
}
