package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleetSwallower extends CardImpl {

    public FleetSwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Fleet Swallower attacks, target player puts the top half of their library, rounded up, into their graveyard.
        Ability ability = new AttacksTriggeredAbility(new MillHalfLibraryTargetEffect(true), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FleetSwallower(final FleetSwallower card) {
        super(card);
    }

    @Override
    public FleetSwallower copy() {
        return new FleetSwallower(this);
    }
}
