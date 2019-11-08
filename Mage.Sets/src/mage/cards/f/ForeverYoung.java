package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ForeverYoung extends CardImpl {

    public ForeverYoung(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Put any number of target creature cards from your graveyard on top of your library.
        this.getSpellAbility().addEffect(new ForeverYoungEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private ForeverYoung(final ForeverYoung card) {
        super(card);
    }

    @Override
    public ForeverYoung copy() {
        return new ForeverYoung(this);
    }
}

class ForeverYoungEffect extends OneShotEffect {

    ForeverYoungEffect() {
        super(Outcome.Benefit);
        staticText = "Put any number of target creature cards from your graveyard on top of your library.";
    }

    private ForeverYoungEffect(final ForeverYoungEffect effect) {
        super(effect);
    }

    @Override
    public ForeverYoungEffect copy() {
        return new ForeverYoungEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.putCardsOnTopOfLibrary(new CardsImpl(
                source.getTargets()
                        .stream()
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .map(game::getCard)
                        .collect(Collectors.toSet())
        ), game, source, true);
    }
}