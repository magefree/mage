
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class EssenceScatter extends CardImpl {

    public EssenceScatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private EssenceScatter(final EssenceScatter card) {
        super(card);
    }

    @Override
    public EssenceScatter copy() {
        return new EssenceScatter(this);
    }
}
