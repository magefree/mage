package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhorClanWrecker extends CardImpl {

    public GhorClanWrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Riot
        this.addAbility(new RiotAbility());

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private GhorClanWrecker(final GhorClanWrecker card) {
        super(card);
    }

    @Override
    public GhorClanWrecker copy() {
        return new GhorClanWrecker(this);
    }
}
