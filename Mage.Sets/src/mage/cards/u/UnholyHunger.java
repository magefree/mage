
package mage.cards.u;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class UnholyHunger extends CardImpl {

    public UnholyHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you gain 2 life.
        Effect effect = new ConditionalOneShotEffect(new GainLifeEffect(2),
                SpellMasteryCondition.instance, "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you gain 2 life");
        this.getSpellAbility().addEffect(effect);
    }

    private UnholyHunger(final UnholyHunger card) {
        super(card);
    }

    @Override
    public UnholyHunger copy() {
        return new UnholyHunger(this);
    }
}
