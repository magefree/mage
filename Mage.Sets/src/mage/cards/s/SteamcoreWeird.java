package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class SteamcoreWeird extends CardImpl {

    public SteamcoreWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.WEIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Steamcore Weird enters the battlefield, if {R} was spent to cast Steamcore Weird, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"))
                .withInterveningIf(ManaWasSpentCondition.RED);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SteamcoreWeird(final SteamcoreWeird card) {
        super(card);
    }

    @Override
    public SteamcoreWeird copy() {
        return new SteamcoreWeird(this);
    }
}
