package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LevelX2
 */
public final class KirtarsWrath extends CardImpl {

    public KirtarsWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Destroy all creatures. They can't be regenerated.
        // Threshold - If seven or more cards are in your graveyard, instead destroy all creatures, then create two 1/1 white Spirit creature tokens with flying. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new KirtarsWrathEffect(),
                new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true),
                new CardsInControllerGraveyardCondition(7),
                "Destroy all creatures. They can't be regenerated.<br/><br/><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, instead destroy all creatures, then create two 1/1 white Spirit creature tokens with flying. Creatures destroyed this way can't be regenerated"));
    }

    private KirtarsWrath(final KirtarsWrath card) {
        super(card);
    }

    @Override
    public KirtarsWrath copy() {
        return new KirtarsWrath(this);
    }
}

class KirtarsWrathEffect extends OneShotEffect {

    public KirtarsWrathEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all creatures, then create two 1/1 white Spirit creature tokens with flying. Creatures destroyed this way can't be regenerated";
    }

    public KirtarsWrathEffect(final KirtarsWrathEffect effect) {
        super(effect);
    }

    @Override
    public KirtarsWrathEffect copy() {
        return new KirtarsWrathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true).apply(game, source);
        return new CreateTokenEffect(new SpiritWhiteToken(), 2).apply(game, source);
    }
}
