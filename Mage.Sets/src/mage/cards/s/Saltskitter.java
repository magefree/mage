
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
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
public final class Saltskitter extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Saltskitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another creature enters the battlefield, exile Saltskitter. Return Saltskitter to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(), filter));
    }

    private Saltskitter(final Saltskitter card) {
        super(card);
    }

    @Override
    public Saltskitter copy() {
        return new Saltskitter(this);
    }
}
