package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderworldRageHound extends CardImpl {

    public UnderworldRageHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Underworld Rage-Hound attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Escape—{3}{R}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{R}", 3));

        // Underworld Rage-Hound escapes with a +1/+1 counter on it.
        this.addAbility(new EscapesWithAbility(1));
    }

    private UnderworldRageHound(final UnderworldRageHound card) {
        super(card);
    }

    @Override
    public UnderworldRageHound copy() {
        return new UnderworldRageHound(this);
    }
}
