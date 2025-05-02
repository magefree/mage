package mage.cards.i;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.RandomUtil;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author jimga150
 */
public final class IndoraptorThePerfectHybrid extends CardImpl {

    public IndoraptorThePerfectHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bloodthirst X (This creature enters the battlefield with X +1/+1 counters on it, where X is the damage
        // dealt to your opponents this turn.)
        this.addAbility(new EntersBattlefieldAbility(
                new IndoraptorThePerfectHybridBloodthirstEffect(),
                "Bloodthirst X "
                        + "<i>(This creature enters the battlefield with X +1/+1 counters on it, "
                        + "where X is the damage dealt to your opponents this turn.)</i>"
        ), new DamageDoneWatcher());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Enrage -- Whenever Indoraptor, the Perfect Hybrid is dealt damage, choose an opponent at random.
        // Indoraptor deals damage equal to its power to that player unless they sacrifice a nontoken creature.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new IndoraptorThePerfectHybridDamageEffect(), false, true));
    }

    private IndoraptorThePerfectHybrid(final IndoraptorThePerfectHybrid card) {
        super(card);
    }

    @Override
    public IndoraptorThePerfectHybrid copy() {
        return new IndoraptorThePerfectHybrid(this);
    }
}

// Copy-pasted PetrifiedWoodKinEffect
class IndoraptorThePerfectHybridBloodthirstEffect extends OneShotEffect {

    IndoraptorThePerfectHybridBloodthirstEffect() {
        super(Outcome.BoostCreature);
        staticText = "";
    }

    private IndoraptorThePerfectHybridBloodthirstEffect(final IndoraptorThePerfectHybridBloodthirstEffect effect) {
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
    public IndoraptorThePerfectHybridBloodthirstEffect copy() {
        return new IndoraptorThePerfectHybridBloodthirstEffect(this);
    }
}

// Based on BellowingMaulerEffect
class IndoraptorThePerfectHybridDamageEffect extends OneShotEffect {

    IndoraptorThePerfectHybridDamageEffect() {
        super(Outcome.Benefit);
        staticText = "choose an opponent at random. " +
                "{this} deals damage equal to its power to that player unless they sacrifice a nontoken creature.";
    }

    private IndoraptorThePerfectHybridDamageEffect(final IndoraptorThePerfectHybridDamageEffect effect) {
        super(effect);
    }

    @Override
    public IndoraptorThePerfectHybridDamageEffect copy() {
        return new IndoraptorThePerfectHybridDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> opponents = new ArrayList<>(game.getOpponents(controller.getId()));
        Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
        if (opponent == null){
            return false;
        }

        boolean sacrificed = false;
        TargetSacrifice targetCreature = new TargetSacrifice(StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN);
        if (targetCreature.canChoose(opponent.getId(), source, game)
                && opponent.chooseUse(Outcome.Sacrifice, "Sacrifice a nontoken creature?", source, game)) {
            opponent.choose(Outcome.Sacrifice, targetCreature, source, game);
            Permanent permanentToSac = game.getPermanent(targetCreature.getFirstTarget());
            sacrificed = permanentToSac != null && permanentToSac.sacrifice(source, game);
        }

        if (!sacrificed) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent == null) {
                return false;
            }
            return opponent.damage(sourcePermanent.getPower().getValue(), source, game) > 0;
        }

        return sacrificed;
    }
}
