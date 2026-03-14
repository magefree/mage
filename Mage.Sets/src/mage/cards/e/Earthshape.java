package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Earthshape extends CardImpl {

    public Earthshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Earthbend 3. Then each creature you control with power less than or equal to that land's power gains hexproof and indestructible until end of turn. You gain hexproof until end of turn.
        this.getSpellAbility().addEffect(new EarthshapeEffect());
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent());
        this.getSpellAbility().addEffect(new GainAbilityControllerEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
    }

    private Earthshape(final Earthshape card) {
        super(card);
    }

    @Override
    public Earthshape copy() {
        return new Earthshape(this);
    }
}

class EarthshapeEffect extends OneShotEffect {

    EarthshapeEffect() {
        super(Outcome.Benefit);
        staticText = "Earthbend 3. Then each creature you control with power less than or equal " +
                "to that land's power gains hexproof and indestructible until end of turn";
    }

    private EarthshapeEffect(final EarthshapeEffect effect) {
        super(effect);
    }

    @Override
    public EarthshapeEffect copy() {
        return new EarthshapeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        EarthbendTargetEffect.doEarthBend(permanent, 3, game, source);
        game.processAction();
        int power = permanent.getPower().getValue();
        FilterPermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, power));
        game.addEffect(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filter), source);
        game.addEffect(new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter), source);
        return true;
    }
}
