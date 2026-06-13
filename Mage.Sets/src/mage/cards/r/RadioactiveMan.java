package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RadioactiveMan extends CardImpl {

    public RadioactiveMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Radioactive Man deals combat damage to a player, that player loses half their life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new LoseHalfLifeTargetEffect(), false, true
        ));
    }

    private RadioactiveMan(final RadioactiveMan card) {
        super(card);
    }

    @Override
    public RadioactiveMan copy() {
        return new RadioactiveMan(this);
    }
}
