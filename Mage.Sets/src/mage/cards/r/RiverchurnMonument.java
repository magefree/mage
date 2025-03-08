package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class RiverchurnMonument extends CardImpl {

    public RiverchurnMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");
        

        // {1}, {T}: Any number of target players each mill two cards.
        Effect effect = new MillCardsTargetEffect(2);
        effect.setTargetPointer(new EachTargetPointer());
        effect.setText("Any number of target players each mill two cards. " +
                "(Each of them puts the top two cards of their library into their graveyard.)");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);

        // Exhaust -- {2}{U}{U}, {T}: Any number of target players each mill cards equal to the number of cards in their graveyard.
        Effect exhaustEffect = new RiverchurnMonumentEffect();
        exhaustEffect.setTargetPointer(new EachTargetPointer());
        exhaustEffect.setText("Any number of target players each mill cards equal to the number of cards in their graveyard.");
        Ability exhaustAbility = new ExhaustAbility(exhaustEffect, new ManaCostsImpl<>("{2}{U}{U}"));
        exhaustAbility.addCost(new TapSourceCost());
        exhaustAbility.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(exhaustAbility);
    }

    private RiverchurnMonument(final RiverchurnMonument card) {
        super(card);
    }

    @Override
    public RiverchurnMonument copy() {
        return new RiverchurnMonument(this);
    }
}

class RiverchurnMonumentEffect extends OneShotEffect {

    public RiverchurnMonumentEffect() {
        super(Outcome.Detriment);
        this.staticText = "Any number of target players each mill cards equal to the number of cards in their graveyard.";
    }

    public RiverchurnMonumentEffect(final RiverchurnMonumentEffect effect) {
        super(effect);
    }

    @Override
    public RiverchurnMonumentEffect copy() {
        return new RiverchurnMonumentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.millCards(player.getGraveyard().size(), source, game);
            }
        }
        return true;
    }
}
