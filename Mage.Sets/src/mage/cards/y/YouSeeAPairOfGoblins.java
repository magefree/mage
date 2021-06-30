package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouSeeAPairOfGoblins extends CardImpl {

    public YouSeeAPairOfGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one —
        // • Charge Them — Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().withFirstModeFlavorWord("Charge Them");

        // • Befriend Them — Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addMode(new Mode(
                new CreateTokenEffect(new GoblinToken(), 2)
        ).withFlavorWord("Befriend Them"));
    }

    private YouSeeAPairOfGoblins(final YouSeeAPairOfGoblins card) {
        super(card);
    }

    @Override
    public YouSeeAPairOfGoblins copy() {
        return new YouSeeAPairOfGoblins(this);
    }
}
