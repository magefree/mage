
package mage.cards.y;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox

 */
public final class YawgmothsEdict extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }


    public YawgmothsEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // Whenever an opponent casts a white spell, that player loses 1 life and you gain 1 life.
        Ability ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1),
            filter, false, SetTargetPointer.PLAYER);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private YawgmothsEdict(final YawgmothsEdict card) {
        super(card);
    }

    @Override
    public YawgmothsEdict copy() {
        return new YawgmothsEdict(this);
    }
}
