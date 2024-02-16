package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BarbedBackWurm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("green creature blocking {this}");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public BarbedBackWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {B}: Target green creature blocking Barbed-Back Wurm gets -1/-1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                -1, -1, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BarbedBackWurm(final BarbedBackWurm card) {
        super(card);
    }

    @Override
    public BarbedBackWurm copy() {
        return new BarbedBackWurm(this);
    }
}
