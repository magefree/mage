

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * Salvage X (X, Exile this card from your graveyard: Draw a card. Salvage only as a sorcery.)
 *
 * @author NinthWorld
 */

public class SalvageAbility extends ActivatedAbilityImpl {

    public SalvageAbility(ManaCosts costs) {
        super(Zone.GRAVEYARD, new SalvageEffect(), costs);
        this.timing = TimingRule.SORCERY;
        this.addCost(new ExileSourceFromGraveCost());
    }

    public SalvageAbility(final SalvageAbility ability) {
        super(ability);
    }

    @Override
    public SalvageAbility copy() {
        return new SalvageAbility(this);
    }

    @Override
    public String getRule() {
        return "Salvage " + getManaCosts().getText() + " <i>(" + getManaCosts().getText() + ", Exile this card from your graveyard: Draw a card. Salvage only as a sorcery.)</i>";
    }
}

class SalvageEffect extends OneShotEffect {

    SalvageEffect() {
        super(Outcome.DrawCard);
    }

    SalvageEffect(final SalvageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (card != null && player != null) {
            player.drawCards(1, game);
            return true;
        }
        return false;
    }

    @Override
    public SalvageEffect copy() {
        return new SalvageEffect(this);
    }
}