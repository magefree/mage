
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
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
public final class PlaneswalkersScorn extends CardImpl {

    public PlaneswalkersScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // {3}{B}: Target opponent reveals a card at random from their hand. Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersScornEffect(), new ManaCostsImpl<>("{3}{B}"));
        Target target = new TargetOpponent();
        ability.addTarget(target);
        target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private PlaneswalkersScorn(final PlaneswalkersScorn card) {
        super(card);
    }

    @Override
    public PlaneswalkersScorn copy() {
        return new PlaneswalkersScorn(this);
    }
}

class PlaneswalkersScornEffect extends OneShotEffect {

    public PlaneswalkersScornEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from their hand. Target creature gets -X/-X until end of turn, where X is the revealed card's mana value";
    }

    public PlaneswalkersScornEffect(final PlaneswalkersScornEffect effect) {
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
                int boostValue = -1 * card.getManaValue();
                opponent.revealCards("Planeswalker's Scorn", revealed, game);
                ContinuousEffect effect = new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersScornEffect copy() {
        return new PlaneswalkersScornEffect(this);
    }

}