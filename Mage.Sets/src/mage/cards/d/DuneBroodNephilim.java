
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DuneBroodNephilimToken;

/**
 * @author Loki
 */
public final class DuneBroodNephilim extends CardImpl {

    static final FilterControlledPermanent filterLands = new FilterControlledLandPermanent();

    public DuneBroodNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}{W}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Dune-Brood Nephilim deals combat damage to a player, create a 1/1 colorless Sand creature token for each land you control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new DuneBroodNephilimToken(), new PermanentsOnBattlefieldCount(filterLands)), false));
    }

    private DuneBroodNephilim(final DuneBroodNephilim card) {
        super(card);
    }

    @Override
    public DuneBroodNephilim copy() {
        return new DuneBroodNephilim(this);
    }
}
