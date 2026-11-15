package mage.cards.c;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestCommanderManaValue;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.Zone;
import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CommandChair extends CardImpl {

    public CommandChair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // This artifact enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add three mana of any one color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()));

        // {T}: Add X mana of any one color, where X is the greatest mana value among commanders you control.
        this.addAbility(new DynamicManaAbility(
            new Mana(0, 0, 0, 0, 0, 0, 1, 0), GreatestCommanderManaValue.instance, new TapSourceCost(),
            "Add X mana of any one color, where X is the greatest mana value among commanders you control", true
        ).addHint(GreatestCommanderManaValue.getHint()));
    }

    private CommandChair(final CommandChair card) {
        super(card);
    }

    @Override
    public CommandChair copy() {
        return new CommandChair(this);
    }
}
