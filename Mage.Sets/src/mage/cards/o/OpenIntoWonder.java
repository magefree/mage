
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OpenIntoWonder extends CardImpl {

    public OpenIntoWonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // X target creatures can't be blocked this turn. Until end of turn, those creatures gain "Whenever this creature deals combat damage to a player, draw a card."
        Effect effect = new CantBeBlockedTargetEffect(Duration.EndOfTurn);
        effect.setText("X target creatures can't be blocked this turn");
        this.getSpellAbility().addEffect(effect);
        Ability abilityToGain = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(abilityToGain, Duration.EndOfTurn,
                "Until end of turn, those creatures gain \"Whenever this creature deals combat damage to a player, draw a card.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private OpenIntoWonder(final OpenIntoWonder card) {
        super(card);
    }

    @Override
    public OpenIntoWonder copy() {
        return new OpenIntoWonder(this);
    }
}
