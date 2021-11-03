package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelligerentGuest extends CardImpl {

    public BelligerentGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Belligerent Guest deals combat damage to a player, create a Blood token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new BloodToken()), false
        ));
    }

    private BelligerentGuest(final BelligerentGuest card) {
        super(card);
    }

    @Override
    public BelligerentGuest copy() {
        return new BelligerentGuest(this);
    }
}
