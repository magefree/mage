
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class Hesitation extends CardImpl {

    public Hesitation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // When a player casts a spell, sacrifice Hesitation and counter that spell.
        Ability ability = new SpellCastAllTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL)
                .setTriggerPhrase("When a player casts a spell, ");
        Effect effect = new CounterTargetEffect();
        effect.setText("and counter that spell");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Hesitation(final Hesitation card) {
        super(card);
    }

    @Override
    public Hesitation copy() {
        return new Hesitation(this);
    }
}
