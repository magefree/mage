package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class MirkwoodNurturer extends CardImpl {

    public MirkwoodNurturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters,
        // return up to one other target permanent you control to its owner's hand.
        // If you do, put a +1/+1 counter on this creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MirkwoodNurturerEffect());
        ability.addTarget(new TargetControlledPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT, false));
        this.addAbility(ability);
    }

    private MirkwoodNurturer(final MirkwoodNurturer card) {
        super(card);
    }

    @Override
    public MirkwoodNurturer copy() {
        return new MirkwoodNurturer(this);
    }
}


class MirkwoodNurturerEffect extends OneShotEffect {

    // Different from "may return" effects
    // not a cost
    // no target do not add counters - otherwise return and add counters
    MirkwoodNurturerEffect() {
        super(Outcome.BoostCreature);
        staticText = "return up to one other target permanent you control to its owner's hand."
                        +" If you do, put a +1/+1 counter on this creature.";
    }

    private MirkwoodNurturerEffect(final MirkwoodNurturerEffect effect) {
        super(effect);
    }

    @Override
    public MirkwoodNurturerEffect copy() {
        return new MirkwoodNurturerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !player.moveCards(permanent, Zone.HAND, source, game)) {
            return false;
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
    }
}