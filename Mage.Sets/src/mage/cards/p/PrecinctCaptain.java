
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author Loki
 */
public final class PrecinctCaptain extends CardImpl {

    public PrecinctCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever Precinct Captain deals combat damage to a player, create a 1/1 white Soldier creature token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new SoldierToken()), false));
    }

    private PrecinctCaptain(final PrecinctCaptain card) {
        super(card);
    }

    @Override
    public PrecinctCaptain copy() {
        return new PrecinctCaptain(this);
    }
}
