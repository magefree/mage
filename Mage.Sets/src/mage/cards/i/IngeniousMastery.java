package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngeniousMastery extends CardImpl {

    public IngeniousMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{U}");

        // You may pay {2}{U} rather than pay this spell's mana cost.
        Ability costAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{2}{U}"));
        this.addAbility(costAbility);

        // If the {2}{U} cost was paid, you draw three cards, then an opponent creates two Treasure tokens and they scry 2. If that cost wasn't paid, you draw X cards.
        this.getSpellAbility().addEffect(new IngeniousMasteryEffect(costAbility.getOriginalId()));
    }

    private IngeniousMastery(final IngeniousMastery card) {
        super(card);
    }

    @Override
    public IngeniousMastery copy() {
        return new IngeniousMastery(this);
    }
}

class IngeniousMasteryEffect extends OneShotEffect {

    private final UUID alternativeCostOriginalID;

    IngeniousMasteryEffect(UUID alternativeCostOriginalID) {
        super(Outcome.Detriment);
        staticText = "if the {2}{U} cost was paid, you draw three cards, then an opponent creates " +
                "two Treasure tokens and they scry 2. If that cost wasn't paid, you draw X cards";
        this.alternativeCostOriginalID = alternativeCostOriginalID;
    }

    private IngeniousMasteryEffect(IngeniousMasteryEffect effect) {
        super(effect);
        this.alternativeCostOriginalID = effect.alternativeCostOriginalID;
    }

    @Override
    public IngeniousMasteryEffect copy() {
        return new IngeniousMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!AlternativeCostSourceAbility.getActivatedStatus(
                game, source, this.alternativeCostOriginalID, false
        )) {
            return player.drawCards(source.getManaCostsToPay().getX(), source, game) > 0;
        }

        player.drawCards(3, source, game);
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (!player.chooseTarget(Outcome.DrawCard, targetOpponent, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        new TreasureToken().putOntoBattlefield(2, game, source, opponent.getId());
        opponent.scry(2, source, game);
        return true;
    }
}
