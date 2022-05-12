package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MasterOfArms extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature blocking {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public MasterOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {1}{W}: Tap target creature blocking Master of Arms.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MasterOfArms(final MasterOfArms card) {
        super(card);
    }

    @Override
    public MasterOfArms copy() {
        return new MasterOfArms(this);
    }
}
