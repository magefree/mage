package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.CyclingDiscardCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CyclingAbility extends ActivatedAbilityImpl {

    private final Cost cost;
    private final String text;

    public CyclingAbility(Cost cost) {
        super(Zone.HAND, new CyclingDrawEffect(), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = "Cycling";
    }

    public CyclingAbility(Cost cost, FilterCard filter, String text) {
        super(Zone.HAND, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = text;
    }

    public CyclingAbility(final CyclingAbility ability) {
        super(ability);
        this.cost = ability.cost;
        this.text = ability.text;
    }

    @Override
    public CyclingAbility copy() {
        return new CyclingAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder rule = new StringBuilder(this.text);
        if (cost instanceof ManaCost) {
            rule.append(' ');
        } else {
            rule.append("&mdash;");
        }
        rule.append(cost.getText()).append(" <i>(").append(super.getRule(true)).append(")</i>");
        return rule.toString();
    }
}

class CyclingDrawEffect extends OneShotEffect {

    CyclingDrawEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card";
    }

    private CyclingDrawEffect(final CyclingDrawEffect effect) {
        super(effect);
    }

    @Override
    public CyclingDrawEffect copy() {
        return new CyclingDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getSourceId());
        if (player == null) {
            return false;
        }
        GameEvent event = GameEvent.getEvent(
                GameEvent.EventType.CYCLE_DRAW, source.getSourceId(),
                source.getSourceId(), source.getControllerId()
        );
        if (game.replaceEvent(event)) {
            return true;
        }
        player.drawCards(1, game);
        return true;
    }
}