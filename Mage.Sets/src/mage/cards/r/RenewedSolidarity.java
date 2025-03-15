package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

public class RenewedSolidarity extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");
    private static final FilterPermanent tokenFilter = new FilterPermanent("tokens you control of the chosen type that entered this turn");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        tokenFilter.add(TargetController.YOU.getControllerPredicate());
        tokenFilter.add(ChosenSubtypePredicate.TRUE);
        tokenFilter.add(TokenPredicate.TRUE);
        tokenFilter.add(EnteredThisTurnPredicate.instance);
    }

    public RenewedSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // As this enchantment enters, choose a creature type.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Creatures you control of the chosen type get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of your end step, for each token you control of the chosen type that entered this turn, create a token that's a copy of it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RenewedSolidarityEffect()));
    }

    public RenewedSolidarity(final RenewedSolidarity card) {
        super(card);
    }

    @Override
    public RenewedSolidarity copy() {
        return new RenewedSolidarity(this);
    }

    class RenewedSolidarityEffect extends OneShotEffect {

        public RenewedSolidarityEffect() {
            super(Outcome.Benefit);
        }

        public RenewedSolidarityEffect(final RenewedSolidarityEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            UUID controllerId = source.getControllerId();
            if (controllerId == null) {
                return false;
            }

            List<Permanent> permanents = game.getBattlefield()
                    .getAllActivePermanents(tokenFilter, controllerId, game);
            permanents
                    .forEach(permanent -> {
                        new CreateTokenCopyTargetEffect(source.getControllerId())
                                .setTargetPointer(new FixedTarget(permanent, game))
                                .apply(game, source);
                    });

            return true;
        }

        @Override
        public RenewedSolidarityEffect copy() {
            return new RenewedSolidarityEffect(this);
        }

        @Override
        public String getText(Mode mode) {
            return "for each token you control of the chosen type that entered this turn, create a token that's a copy of it";
        }
    }
}
