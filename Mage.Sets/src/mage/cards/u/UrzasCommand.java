package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KarnConstructToken;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasCommand extends CardImpl {

    public UrzasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Creatures you don't control get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -2, -0, Duration.EndOfTurn,
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false
        ));

        // * Create a tapped Powerstone token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new PowerstoneToken(), 1, true)));

        // * Create a tapped 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new KarnConstructToken(), 1, true)));

        // * Scry 1, then draw a card.
        this.getSpellAbility().addMode(new Mode(new ScryEffect(1, false))
                .addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then")));
    }

    private UrzasCommand(final UrzasCommand card) {
        super(card);
    }

    @Override
    public UrzasCommand copy() {
        return new UrzasCommand(this);
    }
}
