
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class PlaneswalkersFavor extends CardImpl {

    public PlaneswalkersFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // {3}{G}: Target opponent reveals a card at random from their hand. Target creature gets +X/+X until end of turn, where X is the revealed card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersFavorEffect(), new ManaCostsImpl<>("{3}{G}"));
        Target target = new TargetOpponent();
        ability.addTarget(target);
        target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private PlaneswalkersFavor(final PlaneswalkersFavor card) {
        super(card);
    }

    @Override
    public PlaneswalkersFavor copy() {
        return new PlaneswalkersFavor(this);
    }
}

class PlaneswalkersFavorEffect extends OneShotEffect {

    public PlaneswalkersFavorEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from their hand. Target creature gets +X/+X until end of turn, where X is the revealed card's mana value";
    }

    public PlaneswalkersFavorEffect(final PlaneswalkersFavorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (opponent != null && !opponent.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = opponent.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                int boostValue = card.getManaValue();
                opponent.revealCards("Planeswalker's Favor", revealed, game);
                ContinuousEffect effect = new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersFavorEffect copy() {
        return new PlaneswalkersFavorEffect(this);
    }

}