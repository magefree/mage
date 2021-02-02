
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class WarriorsLesson extends CardImpl {

    public WarriorsLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Until end of turn, up to two target creatures you control each gain "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1),false);
        Effect effect = new GainAbilityTargetEffect(ability, Duration.EndOfTurn);
        effect.setText("Until end of turn, up to two target creatures you control each gain \"Whenever this creature deals combat damage to a player, draw a card.\"");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    private WarriorsLesson(final WarriorsLesson card) {
        super(card);
    }

    @Override
    public WarriorsLesson copy() {
        return new WarriorsLesson(this);
    }
}
