package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgniteTheFuture extends CardImpl {

    public IgniteTheFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Exile the top three cards of your library. Until the end of your next turn, you may play those cards. If this spell was cast from a graveyard, you may play cards this way without paying their mana costs.
        this.getSpellAbility().addEffect(new IgniteTheFutureEffect());

        // Flashback {7}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{R}"), TimingRule.SORCERY));
    }

    private IgniteTheFuture(final IgniteTheFuture card) {
        super(card);
    }

    @Override
    public IgniteTheFuture copy() {
        return new IgniteTheFuture(this);
    }
}

class IgniteTheFutureEffect extends OneShotEffect {

    IgniteTheFutureEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top three cards of your library. " +
                "Until the end of your next turn, you may play those cards. " +
                "If this spell was cast from a graveyard, " +
                "you may play cards this way without paying their mana costs.";
    }

    private IgniteTheFutureEffect(final IgniteTheFutureEffect effect) {
        super(effect);
    }

    @Override
    public IgniteTheFutureEffect copy() {
        return new IgniteTheFutureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (controller == null || spell == null) {
            return false;
        }
        boolean forFree = spell.getFromZone() == Zone.GRAVEYARD;
        Set<Card> cards = controller.getLibrary().getTopCards(game, 3);
        controller.moveCards(cards, Zone.EXILED, source, game);

        cards.stream().forEach(card -> {
            ContinuousEffect effect = new IgniteTheFutureMayPlayEffect(forFree);
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
        });

        return true;
    }
}

class IgniteTheFutureMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;
    private final boolean forFree;

    IgniteTheFutureMayPlayEffect(boolean forFree) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.forFree = forFree;
        if (forFree) {
            this.staticText = "Until the end of your next turn, you may play that card without playing its mana cost.";
        } else {
            this.staticText = "Until the end of your next turn, you may play that card.";
        }
    }

    private IgniteTheFutureMayPlayEffect(final IgniteTheFutureMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
        this.forFree = effect.forFree;
    }

    @Override
    public IgniteTheFutureMayPlayEffect copy() {
        return new IgniteTheFutureMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return castOnTurn != game.getTurnNum()
                && game.getPhase().getStep().getType() == PhaseStep.END_TURN
                && game.isActivePlayer(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || !getTargetPointer().getTargets(game, source).contains(objectId)) {
            return false;
        }
        if (!forFree) {
            return true;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand() || card.getSpellAbility().getCosts() == null) {
            return true;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player != null) {
            player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
        }
        return true;
    }
}
