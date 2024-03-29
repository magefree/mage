package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShackleSlinger extends CardImpl {

    public ShackleSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, choose target creature an opponent controls. If it's tapped, put a stun counter on it. Otherwise, tap it.
        Ability ability = new CastSecondSpellTriggeredAbility(new ShackleSlingerEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ShackleSlinger(final ShackleSlinger card) {
        super(card);
    }

    @Override
    public ShackleSlinger copy() {
        return new ShackleSlinger(this);
    }
}

class ShackleSlingerEffect extends OneShotEffect {

    ShackleSlingerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature an opponent controls. " +
                "If it's tapped, put a stun counter on it. Otherwise, tap it";
    }

    private ShackleSlingerEffect(final ShackleSlingerEffect effect) {
        super(effect);
    }

    @Override
    public ShackleSlingerEffect copy() {
        return new ShackleSlingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (!permanent.isTapped()) {
            return permanent.tap(source, game);
        }
        return permanent.addCounters(CounterType.STUN.createInstance(), source, game);
    }
}
