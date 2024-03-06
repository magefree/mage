package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbsolvingLammasu extends CardImpl {

    public AbsolvingLammasu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.LAMMASU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Absolving Lammasu enters the battlefield, all suspected creatures are no longer suspected.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AbsolvingLammasuEffect()));

        // When Absolving Lammasu dies, you gain 3 life and suspect up to one target creature an opponent controls.
        Ability ability = new DiesSourceTriggeredAbility(new GainLifeEffect(3));
        ability.addEffect(new SuspectTargetEffect().concatBy("and"));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private AbsolvingLammasu(final AbsolvingLammasu card) {
        super(card);
    }

    @Override
    public AbsolvingLammasu copy() {
        return new AbsolvingLammasu(this);
    }
}

class AbsolvingLammasuEffect extends OneShotEffect {

    AbsolvingLammasuEffect() {
        super(Outcome.Benefit);
        staticText = "all suspected creatures are no longer suspected";
    }

    private AbsolvingLammasuEffect(final AbsolvingLammasuEffect effect) {
        super(effect);
    }

    @Override
    public AbsolvingLammasuEffect copy() {
        return new AbsolvingLammasuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent.isSuspected()) {
                permanent.setSuspected(false, game, source);
            }
        }
        return true;
    }
}
