
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class TheTabernacleAtPendrellVale extends CardImpl {

    public TheTabernacleAtPendrellVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // All creatures have "At the beginning of your upkeep, destroy this creature unless you pay {1}."
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DestroySourceUnlessPaysEffect(new ManaCostsImpl("{1}")), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        ability,
                        Duration.WhileOnBattlefield,
                        new FilterCreaturePermanent("All creatures")
                )
        ));
    }

    public TheTabernacleAtPendrellVale(final TheTabernacleAtPendrellVale card) {
        super(card);
    }

    @Override
    public TheTabernacleAtPendrellVale copy() {
        return new TheTabernacleAtPendrellVale(this);
    }
}

class DestroySourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public DestroySourceUnlessPaysEffect(Cost cost) {
        super(Outcome.DestroyPermanent);
        this.cost = cost;
    }

    public DestroySourceUnlessPaysEffect(final DestroySourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null && source.getSourceObjectZoneChangeCounter() == permanent.getZoneChangeCounter(game)) {
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    return true;
                }
            }
            permanent.destroy(source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public DestroySourceUnlessPaysEffect copy() {
        return new DestroySourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "destroy this creature unless you pay {1}";
    }
}
