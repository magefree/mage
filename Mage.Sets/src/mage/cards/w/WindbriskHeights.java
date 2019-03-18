
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public final class WindbriskHeights extends CardImpl {

    public WindbriskHeights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        this.addAbility(new HideawayAbility());
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // {W}, {tap}: You may play the exiled card without paying its mana cost if you attacked with three or more creatures this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new HideawayPlayEffect(), new ManaCostsImpl("{W}"), WindbriskHeightsAttackersCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new PlayerAttackedWatcher());

    }

    public WindbriskHeights(final WindbriskHeights card) {
        super(card);
    }

    @Override
    public WindbriskHeights copy() {
        return new WindbriskHeights(this);
    }
}

enum WindbriskHeightsAttackersCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerAttackedWatcher watcher = game.getState().getWatcher(PlayerAttackedWatcher.class);
        return watcher != null && watcher.getNumberOfAttackersCurrentTurn(source.getControllerId()) >= 3;
    }

    @Override
    public String toString() {
        return "you attacked with three or more creatures this turn";
    }
}
