package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulSquad extends CardImpl {

    public SpitefulSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Spiteful Squad enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"
        ));

        // When Spiteful Squad dies, put its counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new SpitefulSquadEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SpitefulSquad(final SpitefulSquad card) {
        super(card);
    }

    @Override
    public SpitefulSquad copy() {
        return new SpitefulSquad(this);
    }
}

class SpitefulSquadEffect extends OneShotEffect {

    SpitefulSquadEffect() {
        super(Outcome.Benefit);
        staticText = "put its counters on target creature you control";
    }

    private SpitefulSquadEffect(final SpitefulSquadEffect effect) {
        super(effect);
    }

    @Override
    public SpitefulSquadEffect copy() {
        return new SpitefulSquadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = (Permanent) getValue("permanentLeftBattlefield");
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        sourcePermanent
                .getCounters(game)
                .values()
                .stream()
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }
}
