
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class OverwhelmingDenial extends CardImpl {

    public OverwhelmingDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Overwhelming Denial can't be countered by spell or abilities.
        Effect effect = new CantBeCounteredSourceEffect();
        effect.setText("this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK, effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        
        // Has to be placed last here, because added spellAbility objects (e.g. effects) have to be copied from this
        // Surge {U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{U}{U}"));
    }

    private OverwhelmingDenial(final OverwhelmingDenial card) {
        super(card);
    }

    @Override
    public OverwhelmingDenial copy() {
        return new OverwhelmingDenial(this);
    }
}
