package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PlaneswalkersFury extends CardImpl {

    public PlaneswalkersFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {3}{R}: Target opponent reveals a card at random from their hand. Planeswalker's Fury deals damage equal to that card's converted mana cost to that player. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersFuryEffect(), new ManaCostsImpl<>("{3}{R}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PlaneswalkersFury(final PlaneswalkersFury card) {
        super(card);
    }

    @Override
    public PlaneswalkersFury copy() {
        return new PlaneswalkersFury(this);
    }
}

class PlaneswalkersFuryEffect extends OneShotEffect {

    public PlaneswalkersFuryEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from their hand. Planeswalker's Fury deals damage equal to that card's mana value to that player";
    }

    public PlaneswalkersFuryEffect(final PlaneswalkersFuryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent != null && !opponent.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = opponent.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                opponent.revealCards("Planeswalker's Fury", revealed, game);
                opponent.damage(card.getManaValue(), source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersFuryEffect copy() {
        return new PlaneswalkersFuryEffect(this);
    }

}