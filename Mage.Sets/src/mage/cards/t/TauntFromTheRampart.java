package mage.cards.t;

import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TauntFromTheRampart extends CardImpl {

    private static final FilterOpponentsCreaturePermanent filter = new FilterOpponentsCreaturePermanent("");

    public TauntFromTheRampart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{W}");


        // Goad all creatures your opponents control. Until your next turn, those creatures can't block.
        this.getSpellAbility().addEffect(new GoadAllEffect(filter)
                .setText("Goad all creatures your opponents control.")
        );
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.UntilYourNextTurn)
                .setText("Until your next turn, those creatures can't block. "
                        + "<i>(Until your next turn, those creatures attack each combat if able "
                        + "and attack a player other than you if able.)</i>")
        );
    }

    private TauntFromTheRampart(final TauntFromTheRampart card) {
        super(card);
    }

    @Override
    public TauntFromTheRampart copy() {
        return new TauntFromTheRampart(this);
    }
}
