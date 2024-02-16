package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class LunarForce extends CardImpl {

    public LunarForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever an opponent casts a spell, sacrifice Lunar Force and counter that spell.
        Ability ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL);
        Effect effect = new CounterTargetEffect();
        effect.setText("and counter that spell");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private LunarForce(final LunarForce card) {
        super(card);
    }

    @Override
    public LunarForce copy() {
        return new LunarForce(this);
    }
}
