package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VraskaBetrayalsSting extends CardImpl {

    public VraskaBetrayalsSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B/P}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.setStartingLoyalty(6);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // 0: You draw a card and you lose 1 life. Proliferate.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1).setText("You draw a card"), 0);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        ability.addEffect(new ProliferateEffect(false));
        this.addAbility(ability);

        // −2: Target creature becomes a Treasure artifact with "{T}, Sacrifice this artifact:
        // Add one mana of any color" and loses all other card types and abilities.
        ability = new LoyaltyAbility(new BecomesCreatureTargetEffect(
                new TreasureToken(), true, false, Duration.WhileOnBattlefield, false, false, true)
                .setText("Target creature becomes a Treasure artifact with \"{T}, Sacrifice this artifact: " +
                        "Add one mana of any color\" and loses all other card types and abilities"), -2
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −9: If target player has fewer than nine poison counters, they get a number of poison counters equal to the difference.
        ability = new LoyaltyAbility(new VraskaBetrayalsStingEffect(), -9);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VraskaBetrayalsSting(final VraskaBetrayalsSting card) {
        super(card);
    }

    @Override
    public VraskaBetrayalsSting copy() {
        return new VraskaBetrayalsSting(this);
    }
}

class VraskaBetrayalsStingEffect extends OneShotEffect {

    VraskaBetrayalsStingEffect() {
        super(Outcome.Benefit);
        staticText = "If target player has fewer than nine poison counters, they get a number of poison counters equal to the difference";
    }

    private VraskaBetrayalsStingEffect(final VraskaBetrayalsStingEffect effect) {
        super(effect);
    }

    @Override
    public VraskaBetrayalsStingEffect copy() {
        return new VraskaBetrayalsStingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        int totalPoison = targetPlayer.getCounters().getCount(CounterType.POISON);
        if (totalPoison < 9) {
            targetPlayer.addCounters(CounterType.POISON.createInstance(9 - totalPoison), source.getControllerId(), source, game);
        }
        return true;
    }
}
