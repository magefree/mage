
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;

/**
 *
 * @author nantuko
 */
public final class SorinMarkov extends CardImpl {

    public SorinMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);

        this.setStartingLoyalty(4);

        // +2: Sorin Markov deals 2 damage to any target and you gain 2 life.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(2), 2);
        ability1.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability1.addTarget(new TargetAnyTarget());
        this.addAbility(ability1);

        // -3: Target opponent's life total becomes 10.
        LoyaltyAbility ability2 = new LoyaltyAbility(new SorinMarkovEffect(), -3);
        ability2.addTarget(new TargetOpponent());
        this.addAbility(ability2);

        // -7: You control target player during that player's next turn.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ControlTargetPlayerNextTurnEffect(), -7);
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);
    }

    private SorinMarkov(final SorinMarkov card) {
        super(card);
    }

    @Override
    public SorinMarkov copy() {
        return new SorinMarkov(this);
    }
}

class SorinMarkovEffect extends OneShotEffect {

    public SorinMarkovEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent's life total becomes 10";
    }

    public SorinMarkovEffect(SorinMarkovEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.setLife(10, game, source);
            return true;
        }
        return false;
    }

    @Override
    public SorinMarkovEffect copy() {
        return new SorinMarkovEffect(this);
    }
}
