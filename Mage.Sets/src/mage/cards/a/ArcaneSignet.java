package mage.cards.a;

import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneSignet extends CardImpl {

    public ArcaneSignet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add one mana of any color in your commander's color identity.
        this.addAbility(new CommanderColorIdentityManaAbility());
    }

    private ArcaneSignet(final ArcaneSignet card) {
        super(card);
    }

    @Override
    public ArcaneSignet copy() {
        return new ArcaneSignet(this);
    }
}
