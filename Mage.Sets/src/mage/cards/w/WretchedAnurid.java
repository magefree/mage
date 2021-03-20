
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class WretchedAnurid extends CardImpl {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");
    
    static {
        filter.add(AnotherPredicate.instance);
    }

    public WretchedAnurid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield, you lose 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new LoseLifeSourceControllerEffect(1), filter));
    }

    private WretchedAnurid(final WretchedAnurid card) {
        super(card);
    }

    @Override
    public WretchedAnurid copy() {
        return new WretchedAnurid(this);
    }
}
