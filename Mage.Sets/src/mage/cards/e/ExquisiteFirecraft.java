
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class ExquisiteFirecraft extends CardImpl {

    public ExquisiteFirecraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");

        // Exquisite Firecraft deals 4 damage to any target.        
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, Exquisite Firecraft can't be countered.
        ContinuousRuleModifyingEffect cantBeCountered = new CantBeCounteredSourceEffect();
        ConditionalContinuousRuleModifyingEffect conditionalCantBeCountered = new ConditionalContinuousRuleModifyingEffect(cantBeCountered, SpellMasteryCondition.instance);
        conditionalCantBeCountered.setText("<br/><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK, conditionalCantBeCountered);
        this.addAbility(ability);
    }

    private ExquisiteFirecraft(final ExquisiteFirecraft card) {
        super(card);
    }

    @Override
    public ExquisiteFirecraft copy() {
        return new ExquisiteFirecraft(this);
    }
}
