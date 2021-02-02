package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class UrbanEvolution extends CardImpl {

    public UrbanEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");

        //Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));

        //You may play an additional land this turn.
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
    }

    private UrbanEvolution(final UrbanEvolution card) {
        super(card);
    }

    @Override
    public UrbanEvolution copy() {
        return new UrbanEvolution(this);
    }
}
