package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiritFlagshipVessel extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("other target artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public InspiritFlagshipVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 1+
        // At the beginning of combat on your turn, put your choice of a +1/+1 counter or two charge counters on up to one other target artifact.
        Ability ability = new BeginningOfCombatTriggeredAbility(new InspiritFlagshipVesselEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(new StationLevelAbility(1).withLevelAbility(ability));

        // STATION 8+
        // Flying
        // Other artifacts you control have hexproof and indestructible.
        // 5/5
        ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACTS, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACTS, true
        ).setText("and indestructible"));
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(ability)
                .withPT(5, 5));
    }

    private InspiritFlagshipVessel(final InspiritFlagshipVessel card) {
        super(card);
    }

    @Override
    public InspiritFlagshipVessel copy() {
        return new InspiritFlagshipVessel(this);
    }
}

class InspiritFlagshipVesselEffect extends OneShotEffect {

    InspiritFlagshipVesselEffect() {
        super(Outcome.Benefit);
        staticText = "put your choice of a +1/+1 counter or two charge counters on up to one other target artifact";
    }

    private InspiritFlagshipVesselEffect(final InspiritFlagshipVesselEffect effect) {
        super(effect);
    }

    @Override
    public InspiritFlagshipVesselEffect copy() {
        return new InspiritFlagshipVesselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null && permanent.addCounters(
                player.chooseUse(
                        Outcome.BoostCreature, "Put a +1/+1 counter or two charge counters on " +
                                permanent.getLogName() + '?', null,
                        "one +1/+1 counter", "two charge counters", source, game
                ) ? CounterType.P1P1.createInstance() : CounterType.CHARGE.createInstance(2), source, game
        );
    }
}
