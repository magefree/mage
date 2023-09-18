
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RavenousTrap extends CardImpl {

    public RavenousTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had three or more cards put into their graveyard from anywhere this turn, you may pay {0} rather than pay Ravenous Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{0}"), RavenousTrapCondition.instance), new CardsPutIntoGraveyardWatcher());

        // Exile all cards from target player's graveyard.
        this.getSpellAbility().addEffect(new ExileGraveyardAllTargetPlayerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private RavenousTrap(final RavenousTrap card) {
        super(card);
    }

    @Override
    public RavenousTrap copy() {
        return new RavenousTrap(this);
    }
}

enum RavenousTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (watcher.getAmountCardsPutToGraveyard(opponentId) > 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent had three or more cards put into their graveyard from anywhere this turn";
    }
}
