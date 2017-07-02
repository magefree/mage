/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.a;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.abilities.Ability;

/**
 *
 * @author Archer262
 */
public class ActOfHeroism extends CardImpl {
    
    public ActOfHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Untap target creature.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature");
        this.getSpellAbility().addEffect(effect);

        // It gets +2/+2 and can block an additional creature this turn.
        effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("It gets +2/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect((Ability) new CanBlockAdditionalCreatureEffect(), Duration.EndOfTurn);
        effect.setText("and can block an additional creature this turn.");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ActOfHeroism(final ActOfHeroism card) {
        super(card);
    }

    @Override
    public ActOfHeroism copy() {
        return new ActOfHeroism(this);
    }
}
