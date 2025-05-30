
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.CantGainLifeRestOfGameTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class StigmaLasher extends CardImpl {

    public StigmaLasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Whenever Stigma Lasher deals damage to a player, that player can't gain life for the rest of the game.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(
                new CantGainLifeRestOfGameTargetEffect(),
                false, true
        ));

    }

    private StigmaLasher(final StigmaLasher card) {
        super(card);
    }

    @Override
    public StigmaLasher copy() {
        return new StigmaLasher(this);
    }
}