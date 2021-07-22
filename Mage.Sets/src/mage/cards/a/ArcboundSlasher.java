package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.ModularAbility;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundSlasher extends CardImpl {

    public ArcboundSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 4
        this.addAbility(new ModularAbility(this, 4));

        // Riot
        this.addAbility(new RiotAbility());
    }

    private ArcboundSlasher(final ArcboundSlasher card) {
        super(card);
    }

    @Override
    public ArcboundSlasher copy() {
        return new ArcboundSlasher(this);
    }
}
