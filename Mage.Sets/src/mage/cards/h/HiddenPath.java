
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class HiddenPath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public HiddenPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}{G}{G}");

        // Green creatures have forestwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new ForestwalkAbility(false), Duration.WhileOnBattlefield, filter)));
    }

    private HiddenPath(final HiddenPath card) {
        super(card);
    }

    @Override
    public HiddenPath copy() {
        return new HiddenPath(this);
    }
}
