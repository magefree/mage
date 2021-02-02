
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.PlainswalkAbility;
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
public final class AysenHighway extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AysenHighway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}{W}");

        // White creatures have plainswalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAllEffect(new PlainswalkAbility(false), Duration.WhileOnBattlefield, filter)));
    }

    private AysenHighway(final AysenHighway card) {
        super(card);
    }

    @Override
    public AysenHighway copy() {
        return new AysenHighway(this);
    }
}
