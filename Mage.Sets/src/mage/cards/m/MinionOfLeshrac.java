package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MinionOfLeshrac extends CardImpl {

    public MinionOfLeshrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // At the beginning of your upkeep, Minion of Leshrac deals 5 damage to you unless you sacrifice a creature other than Minion of Leshrac. If Minion of Leshrac deals damage to you this way, tap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MinionLeshracEffect(), TargetController.YOU, false));

        // {tap}: Destroy target creature or land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
        this.addAbility(ability);

    }

    private MinionOfLeshrac(final MinionOfLeshrac card) {
        super(card);
    }

    @Override
    public MinionOfLeshrac copy() {
        return new MinionOfLeshrac(this);
    }
}

class MinionLeshracEffect extends OneShotEffect {

    public MinionLeshracEffect() {
        super(Outcome.Sacrifice);
        staticText = "{this} deals 5 damage to you unless you sacrifice a creature other than {this}. If {this} deals damage to you this way, tap it";
    }

    public MinionLeshracEffect(final MinionLeshracEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent minionLeshrac = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && minionLeshrac != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE);
            if (controller.chooseUse(Outcome.AIDontUseIt, "Sacrifice another creature to prevent the damage?", source, game)
                    && cost.canPay(source, source, source.getControllerId(), game)
                    && cost.pay(source, game, source, source.getControllerId(), true)) {
                return true;
            }
            if (controller.damage(5, minionLeshrac.getId(), source, game) > 0) {
                minionLeshrac.tap(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public MinionLeshracEffect copy() {
        return new MinionLeshracEffect(this);
    }
}
