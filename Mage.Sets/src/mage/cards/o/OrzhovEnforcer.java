package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.AfterlifeAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrzhovEnforcer extends CardImpl {

    public OrzhovEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));
    }

    private OrzhovEnforcer(final OrzhovEnforcer card) {
        super(card);
    }

    @Override
    public OrzhovEnforcer copy() {
        return new OrzhovEnforcer(this);
    }
}
