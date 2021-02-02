
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Quercitron
 */
public final class LightOfDay extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public LightOfDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // Black creatures can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAllEffect(Duration.WhileOnBattlefield, filter)));

    }

    private LightOfDay(final LightOfDay card) {
        super(card);
    }

    @Override
    public LightOfDay copy() {
        return new LightOfDay(this);
    }
}
