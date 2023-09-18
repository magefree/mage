package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CelestialRegulator extends CardImpl {

    public CelestialRegulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Celestial Regulator enters the battlefield, choose target creature you don't control and tap it.
        // If you control a creature with a counter on it, the chosen creature doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CelestialRegulatorEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private CelestialRegulator(final CelestialRegulator card) {
        super(card);
    }

    @Override
    public CelestialRegulator copy() {
        return new CelestialRegulator(this);
    }
}

class CelestialRegulatorEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public CelestialRegulatorEffect() {
        super(Outcome.Tap);
        this.staticText = "choose target creature you don't control and tap it. " +
                "If you control a creature with a counter on it, the chosen creature doesn't untap during its controller's next untap step";
    }

    private CelestialRegulatorEffect(final CelestialRegulatorEffect effect) {
        super(effect);
    }

    @Override
    public CelestialRegulatorEffect copy() {
        return new CelestialRegulatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature == null) {
            return false;
        }
        targetCreature.tap(source, game);
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0) {
            game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect(), source);
        }
        return true;
    }
}
