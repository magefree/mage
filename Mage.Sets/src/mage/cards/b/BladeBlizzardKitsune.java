package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladeBlizzardKitsune extends CardImpl {

    public BladeBlizzardKitsune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ninjutsu {3}{W}
        this.addAbility(new NinjutsuAbility("{3}{W}"));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private BladeBlizzardKitsune(final BladeBlizzardKitsune card) {
        super(card);
    }

    @Override
    public BladeBlizzardKitsune copy() {
        return new BladeBlizzardKitsune(this);
    }
}
