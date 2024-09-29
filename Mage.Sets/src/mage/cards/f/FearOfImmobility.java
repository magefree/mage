package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfImmobility extends CardImpl {

    public FearOfImmobility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Fear of Immobility enters, tap up to one target creature. If an opponent controls that creature, put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new FearOfImmobilityEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private FearOfImmobility(final FearOfImmobility card) {
        super(card);
    }

    @Override
    public FearOfImmobility copy() {
        return new FearOfImmobility(this);
    }
}

class FearOfImmobilityEffect extends OneShotEffect {

    FearOfImmobilityEffect() {
        super(Outcome.Benefit);
        staticText = "If an opponent controls that creature, put a stun counter on it";
    }

    private FearOfImmobilityEffect(final FearOfImmobilityEffect effect) {
        super(effect);
    }

    @Override
    public FearOfImmobilityEffect copy() {
        return new FearOfImmobilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null
                && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())
                && permanent.addCounters(CounterType.STUN.createInstance(), source, game);
    }
}
