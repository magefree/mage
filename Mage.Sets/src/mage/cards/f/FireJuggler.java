
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;

/**
 *
 * @author BursegSardaukar
 */
public final class FireJuggler extends CardImpl {
    
    public FireJuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Fire Juggler becomes blocked, clash with an opponent. If you win, Fire Juggler deals 4 damage to each creature blocking it.
        FilterPermanent filter = new FilterPermanent("creature blocking it");
        filter.add(new BlockingAttackerIdPredicate(this.getId()));
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DoIfClashWonEffect(new DamageAllEffect(4,filter)),false));
    }

    private FireJuggler(final FireJuggler card) {
        super(card);
    }

    @Override
    public FireJuggler copy() {
        return new FireJuggler(this);
    }
}
