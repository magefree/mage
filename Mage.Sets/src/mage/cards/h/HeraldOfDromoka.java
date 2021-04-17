
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class HeraldOfDromoka extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("other Warrior creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.WARRIOR.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public HeraldOfDromoka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Other Warrior creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect
            (VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private HeraldOfDromoka(final HeraldOfDromoka card) {
        super(card);
    }

    @Override
    public HeraldOfDromoka copy() {
        return new HeraldOfDromoka(this);
    }
}
