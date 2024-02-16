
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VerdantRebirth extends CardImpl {

    public VerdantRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Until end of turn, target creature gains "When this creature dies, return it to its owner's hand."
        Ability gainedAbility = new DiesSourceTriggeredAbility(new ReturnToHandSourceEffect(), false);
        Effect effect = new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature gains \"When this creature dies, return it to its owner's hand.\"");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private VerdantRebirth(final VerdantRebirth card) {
        super(card);
    }

    @Override
    public VerdantRebirth copy() {
        return new VerdantRebirth(this);
    }
}
