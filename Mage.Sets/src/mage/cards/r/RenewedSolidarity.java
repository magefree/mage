package mage.cards.r;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Jmlundeen
 */
public final class RenewedSolidarity extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RenewedSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        

        // As this enchantment enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(1, 0, Duration.WhileOnBattlefield, filter, false)));

        // At the beginning of your end step, for each token you control of the chosen type that entered this turn, create a token that's a copy of it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RenewedSolidarityEffect()));
    }

    private RenewedSolidarity(final RenewedSolidarity card) {
        super(card);
    }

    @Override
    public RenewedSolidarity copy() {
        return new RenewedSolidarity(this);
    }
}

class RenewedSolidarityEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public RenewedSolidarityEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each token you control of the chosen type that entered this turn, create a token that's a copy of it";
    }

    public RenewedSolidarityEffect(final RenewedSolidarityEffect effect) {
        super(effect);
    }

    @Override
    public RenewedSolidarityEffect copy() {
        return new RenewedSolidarityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        List<Permanent> tokens = game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .filter(permanent -> permanent.hasSubtype(subType, game))
                .collect(Collectors.toList());
        boolean result = false;
        for (Permanent token : tokens) {
            result |= new CreateTokenCopyTargetEffect()
                    .setTargetPointer(new FixedTarget(token, game))
                    .apply(game, source);
        }
        return result;
    }
}