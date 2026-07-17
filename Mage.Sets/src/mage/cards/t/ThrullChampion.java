package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class ThrullChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.THRULL, "Thrull creatures");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.THRULL);

    public ThrullChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Thrull creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // {tap}: Gain control of target Thrull for as long as you control Thrull Champion.
        Ability ability = new SimpleActivatedAbility(new GainControlTargetEffect(Duration.WhileControlled), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
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
