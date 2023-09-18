package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoseRoomTreasurer extends CardImpl {

    public RoseRoomTreasurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Alliance — Whenever another creature enters the battlefield under your control, create a Treasure token if this is the first or second time this ability has resolved this turn. Otherwise, you may pay {X}. When you do, Rose Room Treasurer deals X damage to any target.
        this.addAbility(new AllianceAbility(new RoseRoomTreasurerEffect()), new AbilityResolvedWatcher());
    }

    private RoseRoomTreasurer(final RoseRoomTreasurer card) {
        super(card);
    }

    @Override
    public RoseRoomTreasurer copy() {
        return new RoseRoomTreasurer(this);
    }
}

class RoseRoomTreasurerEffect extends OneShotEffect {

    RoseRoomTreasurerEffect() {
        super(Outcome.Benefit);
        staticText = "create a Treasure token if this is the first or second time this ability has resolved this turn. " +
                "Otherwise, you may pay {X}. When you do, {this} deals X damage to any target";
    }

    private RoseRoomTreasurerEffect(final RoseRoomTreasurerEffect effect) {
        super(effect);
    }

    @Override
    public RoseRoomTreasurerEffect copy() {
        return new RoseRoomTreasurerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityResolvedWatcher.getResolutionCount(game, source) <= 2) {
            return new TreasureToken().putOntoBattlefield(1, game, source);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (!player.chooseUse(Outcome.BoostCreature, "Pay {X}?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(costX));
        if (!cost.pay(source, game, source, source.getControllerId(), false)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(costX), false);
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
