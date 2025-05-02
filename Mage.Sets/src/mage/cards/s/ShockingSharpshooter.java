package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShockingSharpshooter extends CardImpl {

    public ShockingSharpshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever another creature you control enters, this creature deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new DamageTargetEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ShockingSharpshooter(final ShockingSharpshooter card) {
        super(card);
    }

    @Override
    public ShockingSharpshooter copy() {
        return new ShockingSharpshooter(this);
    }
}
