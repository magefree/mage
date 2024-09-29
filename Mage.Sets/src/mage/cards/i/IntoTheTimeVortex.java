package mage.cards.i;

import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IntoTheTimeVortex extends CardImpl {

    public IntoTheTimeVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private IntoTheTimeVortex(final IntoTheTimeVortex card) {
        super(card);
    }

    @Override
    public IntoTheTimeVortex copy() {
        return new IntoTheTimeVortex(this);
    }
}
