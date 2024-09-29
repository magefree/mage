package mage.cards.l;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801 and jeffwadsworth
 */
public final class LightUpTheStage extends CardImpl {

    public LightUpTheStage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(
                2, Duration.UntilEndOfYourNextTurn
        ));

        // Spectacle {R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{R}")));
    }

    private LightUpTheStage(final LightUpTheStage card) {
        super(card);
    }

    @Override
    public LightUpTheStage copy() {
        return new LightUpTheStage(this);
    }
}
