package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrishnakhBrashInstigator extends CardImpl {

    public GrishnakhBrashInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Grishnakh, Brash Instigator enters the battlefield, amass Orcs 2. When you do, until end of turn, gain control of target nonlegendary creature an opponent controls with power less than or equal to the amassed Army's power. Untap that creature. It gains haste until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GrishnakhBrashInstigatorEffect()));
    }

    private GrishnakhBrashInstigator(final GrishnakhBrashInstigator card) {
        super(card);
    }

    @Override
    public GrishnakhBrashInstigator copy() {
        return new GrishnakhBrashInstigator(this);
    }
}

class GrishnakhBrashInstigatorEffect extends OneShotEffect {

    private static class GrishnakhBrashInstigatorPredicate implements Predicate<Permanent> {

        private final Permanent permanent;

        GrishnakhBrashInstigatorPredicate(Permanent permanent) {
            this.permanent = permanent;
        }

        @Override
        public boolean apply(Permanent input, Game game) {
            return input.getPower().getValue() <= permanent.getPower().getValue();
        }
    }

    GrishnakhBrashInstigatorEffect() {
        super(Outcome.Benefit);
        staticText = "amass Orcs 2. When you do, until end of turn, gain control of target nonlegendary creature " +
                "an opponent controls with power less than or equal to the amassed Army's power. " +
                "Untap that creature. It gains haste until end of turn";
    }

    private GrishnakhBrashInstigatorEffect(final GrishnakhBrashInstigatorEffect effect) {
        super(effect);
    }

    @Override
    public GrishnakhBrashInstigatorEffect copy() {
        return new GrishnakhBrashInstigatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = AmassEffect.doAmass(2, SubType.ORC, game, source);
        if (permanent == null) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn)
                        .setText("until end of turn, gain control of target nonlegendary creature " +
                                "an opponent controls with power less than or equal to the amassed Army's power"),
                false
        );
        ability.addEffect(new UntapTargetEffect("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("It gains haste until end of turn"));
        FilterPermanent filter = new FilterOpponentsCreaturePermanent(
                "nonlegendary creature an opponent controls with power " +
                        "less than or equal to the amassed Army's power"
        );
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(new GrishnakhBrashInstigatorPredicate(permanent));
        ability.addTarget(new TargetPermanent(filter));game.fireReflexiveTriggeredAbility(ability,source);return true;
    }
}
