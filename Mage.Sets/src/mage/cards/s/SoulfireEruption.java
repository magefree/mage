package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SoulfireEruption extends CardImpl {

    public SoulfireEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}{R}{R}");

        // Choose any number of target creatures, planeswalkers, and/or players. For each of them, exile the top card of your library, then Soulfire Eruption deals damage equal to that card's converted mana cost to that permanent or player. You may play the exiled cards until the end of your next turn.
        this.getSpellAbility().addEffect(new SoulfireEruptionEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget(0, Integer.MAX_VALUE));
    }

    private SoulfireEruption(final SoulfireEruption card) {
        super(card);
    }

    @Override
    public SoulfireEruption copy() {
        return new SoulfireEruption(this);
    }
}

class SoulfireEruptionEffect extends OneShotEffect {

    SoulfireEruptionEffect() {
        super(Outcome.Benefit);
        staticText = "choose any number of target creatures, planeswalkers, and/or players. " +
                "For each of them, exile the top card of your library, " +
                "then {this} deals damage equal to that card's mana value to that permanent or player. " +
                "You may play the exiled cards until the end of your next turn";
    }

    private SoulfireEruptionEffect(final SoulfireEruptionEffect effect) {
        super(effect);
    }

    @Override
    public SoulfireEruptionEffect copy() {
        return new SoulfireEruptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID targetId : source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())) {
            Permanent permanent = game.getPermanent(targetId);
            Player player = game.getPlayer(targetId);
            if (permanent == null && player == null) {
                continue;
            }
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            controller.moveCards(card, Zone.EXILED, source, game);
            game.addEffect(new SoulfireEruptionCastEffect().setTargetPointer(new FixedTarget(card, game)), source);
            if (card.getManaValue() < 1) {
                continue;
            }
            if (permanent != null) {
                permanent.damage(card.getManaValue(), source.getSourceId(), source, game);
            }
            if (player != null) {
                player.damage(card.getManaValue(), source.getSourceId(), source, game);
            }
        }
        return true;
    }
}

class SoulfireEruptionCastEffect extends AsThoughEffectImpl {

    SoulfireEruptionCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
    }

    private SoulfireEruptionCastEffect(final SoulfireEruptionCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SoulfireEruptionCastEffect copy() {
        return new SoulfireEruptionCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
