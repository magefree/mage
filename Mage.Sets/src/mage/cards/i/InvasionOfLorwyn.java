package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfLorwyn extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent(
            "non-Elf creature an opponent controls with power X or less, where X is the number of lands you control"
    );

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
        filter.add(InvasionOfLorwynPredicate.instance);
    }

    public InvasionOfLorwyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{4}{B}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.w.WinnowingForces.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Lorwyn enters the battlefield, destroy target non-Elf creature an opponent controls with power X or less, where X is the number of lands you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private InvasionOfLorwyn(final InvasionOfLorwyn card) {
        super(card);
    }

    @Override
    public InvasionOfLorwyn copy() {
        return new InvasionOfLorwyn(this);
    }
}

enum InvasionOfLorwynPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getPower().getValue() <= game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                input.getSource().getControllerId(), input.getSource(), game
        );
    }
}