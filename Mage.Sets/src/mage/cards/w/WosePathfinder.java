package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WosePathfinder extends CardImpl {

    public WosePathfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {6}{G}, {T}: Another target creature gets +3/+3 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(3, 3)
                .setText("another target creature gets +3/+3"), new ManaCostsImpl<>("{6}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private WosePathfinder(final WosePathfinder card) {
        super(card);
    }

    @Override
    public WosePathfinder copy() {
        return new WosePathfinder(this);
    }
}
