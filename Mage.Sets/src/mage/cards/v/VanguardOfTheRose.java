package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VanguardOfTheRose extends CardImpl {

    public VanguardOfTheRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice another creature or artifact: Vanguard of the Rose gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT_SHORT_TEXT));
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private VanguardOfTheRose(final VanguardOfTheRose card) {
        super(card);
    }

    @Override
    public VanguardOfTheRose copy() {
        return new VanguardOfTheRose(this);
    }
}
