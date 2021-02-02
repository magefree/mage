package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.watchers.common.LostControlWatcher;

/**
 *
 * @author MarcoMarin
 */
public final class ThrullChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Thrull creatures");

    static {
        filter.add(SubType.THRULL.getPredicate());
    }

    public ThrullChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Thrull creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
        // {tap}: Gain control of target Thrull for as long as you control Thrull Champion.
        ConditionalContinuousEffect ThrullChampionGainControlEffect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom),
                new SourceOnBattlefieldControlUnchangedCondition(),
                "Gain control of target Thrull for as long as you control {this}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, ThrullChampionGainControlEffect, new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);
    }

    private ThrullChampion(final ThrullChampion card) {
        super(card);
    }

    @Override
    public ThrullChampion copy() {
        return new ThrullChampion(this);
    }
}
