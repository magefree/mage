package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoraciousHydra extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public VoraciousHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Voracious Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When Voracious Hydra enters the battlefield, choose one —
        // • Double the number of +1/+1 counters on Voracious Hydra.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VoraciousHydraEffect(), false);

        // • Voracious Hydra fights target creature you don't control.
        Mode mode = new Mode(
                new FightTargetSourceEffect()
                        .setText("{this} fights target creature you don't control")
        );
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private VoraciousHydra(final VoraciousHydra card) {
        super(card);
    }

    @Override
    public VoraciousHydra copy() {
        return new VoraciousHydra(this);
    }
}

class VoraciousHydraEffect extends OneShotEffect {

    VoraciousHydraEffect() {
        super(Outcome.Benefit);
        staticText = "Double the number of +1/+1 counters on {this}";
    }

    private VoraciousHydraEffect(final VoraciousHydraEffect effect) {
        super(effect);
    }

    @Override
    public VoraciousHydraEffect copy() {
        return new VoraciousHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.P1P1)
        ), source, game);
    }
}