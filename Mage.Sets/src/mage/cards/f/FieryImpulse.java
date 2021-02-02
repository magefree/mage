
package mage.cards.f;

import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FieryImpulse extends CardImpl {

    public FieryImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Fiery Impulse deals 2 damage to target creature.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, Fiery Impulse deals 3 damage to that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3), new DamageTargetEffect(2), SpellMasteryCondition.instance,
                "{this} deals 2 damage to target creature. <br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, {this} deals 3 damage instead"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FieryImpulse(final FieryImpulse card) {
        super(card);
    }

    @Override
    public FieryImpulse copy() {
        return new FieryImpulse(this);
    }
}
