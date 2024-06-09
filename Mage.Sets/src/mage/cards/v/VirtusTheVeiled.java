
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class VirtusTheVeiled extends CardImpl {

    public VirtusTheVeiled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Partner with Gorm the Great (When this creature enters the battlefield, target player may put Gorm into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Gorm the Great", true));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Virtus the Veiled deals combat damage to a player, that player loses half their life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseHalfLifeTargetEffect(), false, true));
    }

    private VirtusTheVeiled(final VirtusTheVeiled card) {
        super(card);
    }

    @Override
    public VirtusTheVeiled copy() {
        return new VirtusTheVeiled(this);
    }
}
