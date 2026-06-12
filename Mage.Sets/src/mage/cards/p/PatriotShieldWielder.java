package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PatriotShieldWielder extends CardImpl {

    public PatriotShieldWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, {T}: Another target creature you control gets +2/+0 and gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("Another target creature you control gets +2/+0"),
            new ManaCostsImpl<>("{2}")
        );
        ability.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
            .setText("and gains hexproof until end of turn"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private PatriotShieldWielder(final PatriotShieldWielder card) {
        super(card);
    }

    @Override
    public PatriotShieldWielder copy() {
        return new PatriotShieldWielder(this);
    }
}
