package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward
 */
public final class BalefireDragon extends CardImpl {

    public BalefireDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Balefire Dragon deals combat damage to a player,
        // it deals that much damage to each creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DamageAllControlledTargetEffect(SavedDamageValue.MUCH, "it"),
                false, true));
    }

    private BalefireDragon(final BalefireDragon card) {
        super(card);
    }

    @Override
    public BalefireDragon copy() {
        return new BalefireDragon(this);
    }
}
