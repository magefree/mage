package mage.cards.t;

import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThroesOfChaos extends CardImpl {

    public ThroesOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private ThroesOfChaos(final ThroesOfChaos card) {
        super(card);
    }

    @Override
    public ThroesOfChaos copy() {
        return new ThroesOfChaos(this);
    }
}
