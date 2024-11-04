package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SoulShackledZombie extends CardImpl {

    public SoulShackledZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When this creature enters, exile up to two target cards from a single graveyard. If at least one creature card was exiled this way, each opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SoulShackledZombieEffect());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD));
        this.addAbility(ability);
    }

    private SoulShackledZombie(final SoulShackledZombie card) {
        super(card);
    }

    @Override
    public SoulShackledZombie copy() {
        return new SoulShackledZombie(this);
    }
}

class SoulShackledZombieEffect extends OneShotEffect {

    SoulShackledZombieEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to two target cards from a single graveyard. "
                + "If at least one creature card was exiled this way, "
                + "each opponent loses 2 life and you gain 2 life.";
    }

    private SoulShackledZombieEffect(final SoulShackledZombieEffect effect) {
        super(effect);
    }

    @Override
    public SoulShackledZombieEffect copy() {
        return new SoulShackledZombieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    cards.add(card);
                }
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            if (card != null && card.isCreature(game)) {
                player.gainLife(2, game, source);
                for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        opponent.loseLife(2, game, source, false);
                    }
                }
                break;
            }
        }
        return true;
    }
}