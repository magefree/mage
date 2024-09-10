package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZephyrWinder extends CardImpl {

    public ZephyrWinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Zephyr Winder deals combat damage to a player, untap up to one target creature.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ZephyrWinder(final ZephyrWinder card) {
        super(card);
    }

    @Override
    public ZephyrWinder copy() {
        return new ZephyrWinder(this);
    }
}
