package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GodsHallGuardian extends CardImpl {

    public GodsHallGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Foretell {3}{W}
        this.addAbility(new ForetellAbility(this, "{3}{W}"));
    }

    private GodsHallGuardian(final GodsHallGuardian card) {
        super(card);
    }

    @Override
    public GodsHallGuardian copy() {
        return new GodsHallGuardian(this);
    }
}
