
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class MudbrawlerRaiders extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public MudbrawlerRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/G}{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mudbrawler Raiders can't be blocked by blue creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
        
    }

    private MudbrawlerRaiders(final MudbrawlerRaiders card) {
        super(card);
    }

    @Override
    public MudbrawlerRaiders copy() {
        return new MudbrawlerRaiders(this);
    }
}
