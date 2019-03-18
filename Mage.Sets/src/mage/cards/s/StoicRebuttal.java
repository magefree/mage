

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 * @author ayrat
 */
public final class StoicRebuttal extends CardImpl {

    public StoicRebuttal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // <i>Metalcraft</i> &mdash; Stoic Rebuttal costs {1} less to cast if you control three or more artifacts.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(1, MetalcraftCondition.instance));
        ability.setRuleAtTheTop(true);
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        this.addAbility(ability);
        
        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public StoicRebuttal(final StoicRebuttal card) {
        super(card);
    }

    @Override
    public StoicRebuttal copy() {
        return new StoicRebuttal(this);
    }
}
