
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Spawnwrithe extends CardImpl {

    public Spawnwrithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Spawnwrithe deals combat damage to a player, create a token that's a copy of Spawnwrithe.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenCopySourceEffect(), false));

    }

    private Spawnwrithe(final Spawnwrithe card) {
        super(card);
    }

    @Override
    public Spawnwrithe copy() {
        return new Spawnwrithe(this);
    }
}
