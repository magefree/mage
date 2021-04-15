package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
public final class StarPupil extends CardImpl {

    public StarPupil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Star Pupil enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"
        ));

        // When Star Pupil dies, put its counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new StarPupilEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private StarPupil(final StarPupil card) {
        super(card);
    }

    @Override
    public StarPupil copy() {
        return new StarPupil(this);
    }
}

class StarPupilEffect extends OneShotEffect {

    StarPupilEffect() {
        super(Outcome.Benefit);
        staticText = "put its counters on target creature you control";
    }

    private StarPupilEffect(final StarPupilEffect effect) {
        super(effect);
    }

    @Override
    public StarPupilEffect copy() {
        return new StarPupilEffect(this);
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
