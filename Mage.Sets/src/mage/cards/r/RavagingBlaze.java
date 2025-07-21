
package mage.cards.r;

import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RavagingBlaze extends CardImpl {

    public RavagingBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}{R}");

        // Ravaging Blaze deals X damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, Ravaging Blaze also deals X damage to that creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetControllerEffect(GetXValue.instance),
            SpellMasteryCondition.instance, "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, {this} also deals X damage to that creature's controller."));
    }

    private RavagingBlaze(final RavagingBlaze card) {
        super(card);
    }

    @Override
    public RavagingBlaze copy() {
        return new RavagingBlaze(this);
    }
}
