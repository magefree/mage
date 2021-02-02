
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author markedagain
 */
public final class CabalSlaver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public CabalSlaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a Goblin deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new DiscardTargetEffect(1), filter, false, SetTargetPointer.NONE, true, true));
    }

    private CabalSlaver(final CabalSlaver card) {
        super(card);
    }

    @Override
    public CabalSlaver copy() {
        return new CabalSlaver(this);
    }
}
