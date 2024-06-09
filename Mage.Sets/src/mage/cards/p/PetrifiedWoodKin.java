
package mage.cards.p;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author TheElk801
 */
public final class PetrifiedWoodKin extends CardImpl {

    private static final FilterCard filter = new FilterCard("instants");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public PetrifiedWoodKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Petrified Wood-Kin can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Bloodthirst X
        this.addAbility(new EntersBattlefieldAbility(
                new PetrifiedWoodKinEffect(),
                "Bloodthirst X "
                + "<i>(This creature enters the battlefield with X +1/+1 counters on it, "
                + "where X is the damage dealt to your opponents this turn.)</i>"
        ), new DamageDoneWatcher());

        // Protection from instants
        this.addAbility(new ProtectionAbility(filter));
    }

    private PetrifiedWoodKin(final PetrifiedWoodKin card) {
        super(card);
    }

    @Override
    public PetrifiedWoodKin copy() {
        return new PetrifiedWoodKin(this);
    }
}

class PetrifiedWoodKinEffect extends OneShotEffect {

    PetrifiedWoodKinEffect() {
        super(Outcome.BoostCreature);
        staticText = "";
    }

    private PetrifiedWoodKinEffect(final PetrifiedWoodKinEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        DamageDoneWatcher watcher = game.getState().getWatcher(DamageDoneWatcher.class);
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || watcher == null || permanent == null) {
            return false;
        }
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
        int amount = 0;
        for (UUID opponentId : game.getOpponents(player.getId())) {
            MageObjectReference mor = new MageObjectReference(opponentId, game);
            amount += watcher.getDamagedObjects().getOrDefault(mor, 0);
        }
        permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
        return true;
    }

    @Override
    public PetrifiedWoodKinEffect copy() {
        return new PetrifiedWoodKinEffect(this);
    }
}
