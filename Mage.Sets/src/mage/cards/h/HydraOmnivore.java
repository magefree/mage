package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageEachOtherOpponentThatMuchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HydraOmnivore extends CardImpl {

    public HydraOmnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Whenever Hydra Omnivore deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new DamageEachOtherOpponentThatMuchEffect(), false, true, true
        ));
    }

    private HydraOmnivore(final HydraOmnivore card) {
        super(card);
    }

    @Override
    public HydraOmnivore copy() {
        return new HydraOmnivore(this);
    }
}
