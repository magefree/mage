package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class ShockmawDragon extends CardImpl {

    public ShockmawDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shockmaw Dragon deals combat damage to a player,
        // it deals 1 damage to each creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DamageAllControlledTargetEffect(1, "it"),
                false, true));
    }

    private ShockmawDragon(final ShockmawDragon card) {
        super(card);
    }

    @Override
    public ShockmawDragon copy() {
        return new ShockmawDragon(this);
    }
}
