package mage.cards.r;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RobTheArchives extends CardImpl {

    public RobTheArchives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(1));

        // Exile the top two cards of your library. You may play those cards this turn.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(2, Duration.EndOfTurn));
    }

    private RobTheArchives(final RobTheArchives card) {
        super(card);
    }

    @Override
    public RobTheArchives copy() {
        return new RobTheArchives(this);
    }
}
