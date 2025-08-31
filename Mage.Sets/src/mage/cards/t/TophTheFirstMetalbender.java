package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TophTheFirstMetalbender extends CardImpl {

    public TophTheFirstMetalbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nontoken artifacts you control are lands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new TophTheFirstMetalbenderEffect()));

        // At the beginning of your end step, earthbend 2.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private TophTheFirstMetalbender(final TophTheFirstMetalbender card) {
        super(card);
    }

    @Override
    public TophTheFirstMetalbender copy() {
        return new TophTheFirstMetalbender(this);
    }
}

class TophTheFirstMetalbenderEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    TophTheFirstMetalbenderEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "nontoken artifacts you control are lands in addition to their other types";
        this.dependendToTypes.add(DependencyType.ArtifactAddingRemoving);
        this.dependencyTypes.add(DependencyType.BecomeNonbasicLand);
    }

    private TophTheFirstMetalbenderEffect(final TophTheFirstMetalbenderEffect effect) {
        super(effect);
    }

    @Override
    public TophTheFirstMetalbenderEffect copy() {
        return new TophTheFirstMetalbenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.addCardType(game, CardType.LAND);
        }
        return true;
    }
}
