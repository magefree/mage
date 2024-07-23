package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IridescentVinelasher extends CardImpl {

    public IridescentVinelasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // Landfall -- Whenever a land you control enters, this creature deals 1 damage to target opponent.
        Ability ability = new LandfallAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private IridescentVinelasher(final IridescentVinelasher card) {
        super(card);
    }

    @Override
    public IridescentVinelasher copy() {
        return new IridescentVinelasher(this);
    }
}
