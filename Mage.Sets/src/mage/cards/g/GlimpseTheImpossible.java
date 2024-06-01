package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GlimpseTheImpossible extends CardImpl {

    public GlimpseTheImpossible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top three cards of your library. You may play those cards this turn. At the beginning of the next end step, if any of those cards remain exiled, put them into your graveyard, then create a 0/1 colorless Eldrazi Spawn creature token for each card put into your graveyard this way. Those tokens have "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addEffect(
                new ExileTopXMayPlayUntilEffect(3, Duration.EndOfTurn)
                        .withTextOptions("those cards", true)
        );
        this.getSpellAbility().addEffect(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                                new GlimpseTheImpossibleEffect(),
                                TargetController.ANY,
                                GlimpseTheImpossibleCondition.instance
                        )
                )
        );
    }

    private GlimpseTheImpossible(final GlimpseTheImpossible card) {
        super(card);
    }

    @Override
    public GlimpseTheImpossible copy() {
        return new GlimpseTheImpossible(this);
    }
}

enum GlimpseTheImpossibleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && !exileZone.isEmpty();
    }

    @Override
    public String toString() {
        return "if any of those cards remain exiled";
    }
}

class GlimpseTheImpossibleEffect extends OneShotEffect {

    GlimpseTheImpossibleEffect() {
        super(Outcome.Benefit);
        staticText = "put them into your graveyard, then create a 0/1 colorless Eldrazi Spawn creature token "
                + "for each card put into your graveyard this way. "
                + "Those tokens have \"Sacrifice this creature: Add {C}.\"";
    }

    private GlimpseTheImpossibleEffect(final GlimpseTheImpossibleEffect effect) {
        super(effect);
    }

    @Override
    public GlimpseTheImpossibleEffect copy() {
        return new GlimpseTheImpossibleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (controller == null || exileZone == null) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone.getCards(game));
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        new CreateTokenEffect(new EldraziSpawnToken(), cards.size())
                .apply(game, source);
        return true;
    }

}