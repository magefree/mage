package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class AirbendTargetEffect extends OneShotEffect {

    public AirbendTargetEffect() {
        super(Outcome.Benefit);
    }

    private AirbendTargetEffect(final AirbendTargetEffect effect) {
        super(effect);
    }

    @Override
    public AirbendTargetEffect copy() {
        return new AirbendTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (player == null || permanents.isEmpty()) {
            return false;
        }
        player.moveCards(permanents, Zone.EXILED, source, game);
        Cards cards = new CardsImpl(permanents);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            game.addEffect(new AirbendingCastEffect(card, game), source);
        }
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.AIRBENDED, source.getSourceId(),
                source, source.getControllerId()
        ));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "airbend " + getTargetPointer().describeTargets(mode.getTargets(), "");
    }
}

class AirbendingCastEffect extends AsThoughEffectImpl {

    AirbendingCastEffect(Card card, Game game) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.AIDontUseIt);
        this.setTargetPointer(new FixedTarget(card, game));
    }

    private AirbendingCastEffect(final AirbendingCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AirbendingCastEffect copy() {
        return new AirbendingCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            discard();
            return false;
        }
        if (!card.getId().equals(objectId) || !card.isOwnedBy(affectedControllerId)) {
            return false;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(card.getSpellAbility().getCosts());
        player.setCastSourceIdWithAlternateMana(card.getId(), new ManaCostsImpl<>("{2}"), newCosts);
        return true;
    }
}
