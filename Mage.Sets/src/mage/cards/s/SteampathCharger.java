package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteampathCharger extends CardImpl {

    public SteampathCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // When this creature dies, it deals 1 damage to target player.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SteampathCharger(final SteampathCharger card) {
        super(card);
    }

    @Override
    public SteampathCharger copy() {
        return new SteampathCharger(this);
    }
}
