package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class StreamOfConsciousness extends CardImpl {

    public StreamOfConsciousness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new StreamOfConsciousnessEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new StreamOfConsciousnessTarget());

    }

    private StreamOfConsciousness(final StreamOfConsciousness card) {
        super(card);
    }

    @Override
    public StreamOfConsciousness copy() {
        return new StreamOfConsciousness(this);
    }
}

class StreamOfConsciousnessEffect extends OneShotEffect {

    public StreamOfConsciousnessEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to four target cards from their graveyard into their library";
    }

    public StreamOfConsciousnessEffect(final StreamOfConsciousnessEffect effect) {
        super(effect);
    }

    @Override
    public StreamOfConsciousnessEffect copy() {
        return new StreamOfConsciousnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Cards targets = new CardsImpl(source.getTargets().get(1).getTargets());
            targets.retainAll(player.getGraveyard());
            return player.shuffleCardsToLibrary(targets, game, source);
        }
        return false;
    }
}

class StreamOfConsciousnessTarget extends TargetCardInGraveyard {

    public StreamOfConsciousnessTarget() {
        super(0, 4, new FilterCard("cards from target player's graveyard"));
    }

    public StreamOfConsciousnessTarget(final StreamOfConsciousnessTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public StreamOfConsciousnessTarget copy() {
        return new StreamOfConsciousnessTarget(this);
    }
}
