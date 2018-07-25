package mage.cards.h;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author NinthWorld
 */
public final class HiveCluster extends CardImpl {

    public HiveCluster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // As Hive Cluster enters the battlefield, you may reveal two creature cards in your hand. If you don't, Hive Cluster enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new HiveClusterEffect()));

        // {T}: Add {B} or {G} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost()));

    }

    public HiveCluster(final HiveCluster card) {
        super(card);
    }

    @Override
    public HiveCluster copy() {
        return new HiveCluster(this);
    }
}


class HiveClusterEffect extends OneShotEffect {

    public HiveClusterEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reveal two creature cards in your hand. If you don't, {this} enters the battlefield tapped";
    }

    public HiveClusterEffect(final HiveClusterEffect effect) {
        super(effect);
    }

    @Override
    public HiveClusterEffect copy() {
        return new HiveClusterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: Check if this configuration actually works. If not, set min to 0 and add "|| cost.getNumberRevealedCards() == 2" to if-statement
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(2, 2, StaticFilters.FILTER_CARD_CREATURE));
        if (!cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
            source.addEffect(new TapSourceEffect());
        }
        return true;
    }
}
