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
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class BalefulMastery extends CardImpl {

    public BalefulMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // You may pay {1}{B} rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{1}{B}")));

        // If the {1}{B} cost was paid, an opponent draws a card.
        this.getSpellAbility().addEffect(new BalefulMasteryAlternativeCostEffect());

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

    BalefulMasteryAlternativeCostEffect() {
        super(Outcome.Detriment);
        staticText = "if the {1}{B} cost was paid, an opponent draws a card.<br>";
    }

    BalefulMasteryAlternativeCostEffect(BalefulMasteryAlternativeCostEffect effect) {
        super(effect);
    }

    @Override
    public BalefulMasteryAlternativeCostEffect copy() {
        return new BalefulMasteryAlternativeCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getOriginalId());
        AlternativeCostSourceAbility altCostAbility = (AlternativeCostSourceAbility) spell.getAbilities().stream()
            .filter(ability -> ability instanceof AlternativeCostSourceAbility)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
        Object value = game.getState().getValue(altCostAbility.getId().toString());
        if (value != null && value.toString().equals("ALT_COST_PAID")) {
            Player player = game.getPlayer(source.getControllerId());
            TargetOpponent targetOpponent = new TargetOpponent(true);
            if (player.chooseTarget(Outcome.Detriment, targetOpponent, source, game)) {
                Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
                opponent.drawCards(1, source, game);
                return true;
            }
            return false;
        }
        return true;
    }
}