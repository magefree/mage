package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class BalefulMastery extends CardImpl {

    public BalefulMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // You may pay {1}{B} rather than pay this spell's mana cost.
        Ability costAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{1}{B}"));
        this.addAbility(costAbility);

        // If the {1}{B} cost was paid, an opponent draws a card.
        this.getSpellAbility().addEffect(new BalefulMasteryAlternativeCostEffect(costAbility.getOriginalId()));

        // Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BalefulMastery(final BalefulMastery card) {
        super(card);
    }

    @Override
    public BalefulMastery copy() {
        return new BalefulMastery(this);
    }
}

class BalefulMasteryAlternativeCostEffect extends OneShotEffect {

    private final UUID alternativeCostOriginalID;

    BalefulMasteryAlternativeCostEffect(UUID alternativeCostOriginalID) {
        super(Outcome.Detriment);
        staticText = "if the {1}{B} cost was paid, an opponent draws a card.<br>";
        this.alternativeCostOriginalID = alternativeCostOriginalID;
    }

    private BalefulMasteryAlternativeCostEffect(BalefulMasteryAlternativeCostEffect effect) {
        super(effect);
        this.alternativeCostOriginalID = effect.alternativeCostOriginalID;
    }

    @Override
    public BalefulMasteryAlternativeCostEffect copy() {
        return new BalefulMasteryAlternativeCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AlternativeCostSourceAbility.getActivatedStatus(
                game, source, this.alternativeCostOriginalID, false
        )) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (player.chooseTarget(Outcome.DrawCard, targetOpponent, source, game)) {
            Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
            if (opponent != null) {
                opponent.drawCards(1, source, game);
                return true;
            }
        }
        return false;
    }
}