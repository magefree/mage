package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlisteningDawn extends CardImpl {

    public GlisteningDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Incubate X twice, where X is the number of lands you control.
        this.getSpellAbility().addEffect(new GlisteningDawnEffect());
    }

    private GlisteningDawn(final GlisteningDawn card) {
        super(card);
    }

    @Override
    public GlisteningDawn copy() {
        return new GlisteningDawn(this);
    }
}

class GlisteningDawnEffect extends OneShotEffect {

    GlisteningDawnEffect() {
        super(Outcome.Benefit);
        staticText = "incubate X twice, where X is the number of lands you control. " +
                "<i>(To incubate X, create an incubator token with X +1/+1 counters on it " +
                "and \"{2}: Transform this artifact.\" It transforms into a 0/0 Phyrexian artifact creature.)</i>";
    }

    private GlisteningDawnEffect(final GlisteningDawnEffect effect) {
        super(effect);
    }

    @Override
    public GlisteningDawnEffect copy() {
        return new GlisteningDawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lands = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
        IncubateEffect.doIncubate(lands, game, source);
        IncubateEffect.doIncubate(lands, game, source);
        return true;
    }
}
