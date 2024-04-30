package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class LegateLaniusCaesarsAce extends CardImpl {

    public LegateLaniusCaesarsAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Decimate -- When Legate Lanius enters the battlefield, each opponent sacrifices a tenth of the creatures they control, rounded up.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LegateLaniusCaesarsAceSacrificeEffect()).withFlavorWord("Decimate"));

        // Whenever an opponent sacrifices a creature, put a +1/+1 counter on Legate Lanius.
        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, TargetController.OPPONENT, SetTargetPointer.NONE, false
        ));
    }

    private LegateLaniusCaesarsAce(final LegateLaniusCaesarsAce card) {
        super(card);
    }

    @Override
    public LegateLaniusCaesarsAce copy() {
        return new LegateLaniusCaesarsAce(this);
    }
}

//Based on SacrificeAllEffect
class LegateLaniusCaesarsAceSacrificeEffect extends OneShotEffect {
    LegateLaniusCaesarsAceSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent sacrifices a tenth of the creatures they control, rounded up.";
    }

    private LegateLaniusCaesarsAceSacrificeEffect(final LegateLaniusCaesarsAceSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public LegateLaniusCaesarsAceSacrificeEffect copy() {
        return new LegateLaniusCaesarsAceSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> perms = new HashSet<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(playerId));

            // 1/10 rounded up
            int num = (game.getBattlefield().count(filter, playerId, source, game) + 9) / 10;
            int numTargets = Math.min(num, game.getBattlefield().count(TargetSacrifice.makeFilter(filter), playerId, source, game));
            if (numTargets < 1) {
                continue;
            }
            TargetSacrifice target = new TargetSacrifice(numTargets, filter);
            while (!target.isChosen() && target.canChoose(playerId, source, game) && player.canRespond()) {
                player.choose(Outcome.Sacrifice, target, source, game);
            }
            perms.addAll(target.getTargets());
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
