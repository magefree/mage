
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Styxo
 */
public final class ShadowTrooper extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Trooper creatures");

    static {
        filter.add(new SubtypePredicate(SubType.TROOPER));
    }

    public ShadowTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash        
        this.addAbility(FlashAbility.getInstance());

        // You may cast Trooper creature cards as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

    }

    public ShadowTrooper(final ShadowTrooper card) {
        super(card);
    }

    @Override
    public ShadowTrooper copy() {
        return new ShadowTrooper(this);
    }
}
