
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import static mage.cards.r.RavenousWampa.RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class RavenousWampa extends CardImpl {

    static final String RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX = "TOU_SAC_CRE";

    public RavenousWampa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{G}, Sacrifice another creature: Monstrosity 2.
        Ability ability = new MonstrosityAbility("{2}{G}", 2);
        ability.addCost(new RavenousWampaSacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);

        // When Ravenous Wampa becomes monstrous, you gain life equal to the sacrificied creature's toughness.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new RavenousWampaEffect()));
    }

    private RavenousWampa(final RavenousWampa card) {
        super(card);
    }

    @Override
    public RavenousWampa copy() {
        return new RavenousWampa(this);
    }
}

class RavenousWampaEffect extends OneShotEffect {

    public RavenousWampaEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain life equal to the sacrificied creature's toughness";
    }

    private RavenousWampaEffect(final RavenousWampaEffect effect) {
        super(effect);
    }

    @Override
    public RavenousWampaEffect copy() {
        return new RavenousWampaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Integer toughness = (Integer) game.getState().getValue(RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX + source.getSourceId() + sourceObject.getZoneChangeCounter(game));
            if (toughness != null) {
                controller.gainLife(toughness, game, source);
            }
            return true;
        }
        return false;
    }
}

class RavenousWampaSacrificeTargetCost extends SacrificeTargetCost {

    public RavenousWampaSacrificeTargetCost(TargetControlledPermanent target) {
        super(target);
    }

    private RavenousWampaSacrificeTargetCost(final RavenousWampaSacrificeTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        boolean result = super.pay(ability, game, source, controllerId, noMana, costToPay);
        if (paid && !getPermanents().isEmpty()) {
            Permanent sacrificedPermanen = getPermanents().get(0);
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && sacrificedPermanen != null) {
                game.getState().setValue(RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX + source.getSourceId() + sourcePermanent.getZoneChangeCounter(game), sacrificedPermanen.getToughness().getValue());
            }
        }
        return result;
    }

    @Override
    public RavenousWampaSacrificeTargetCost copy() {
        return new RavenousWampaSacrificeTargetCost(this);
    }

}
