
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class EmperorsVanguard extends CardImpl {

    public EmperorsVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Emperor's Vanguard deals combat damage to a player, it explores.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExploreSourceEffect(), false));
    }

    private EmperorsVanguard(final EmperorsVanguard card) {
        super(card);
    }

    @Override
    public EmperorsVanguard copy() {
        return new EmperorsVanguard(this);
    }
}
