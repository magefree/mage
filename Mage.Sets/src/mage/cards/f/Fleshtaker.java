package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fleshtaker extends CardImpl {

    public Fleshtaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice another creature, you gain 1 life and scry 1.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        );
        ability.addEffect(new ScryEffect(1, false).concatBy("and"));
        this.addAbility(ability);

        // {1}, Sacrifice another creature: Fleshtaker gets +2/+2 until end of turn.
        ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        this.addAbility(ability);
    }

    private Fleshtaker(final Fleshtaker card) {
        super(card);
    }

    @Override
    public Fleshtaker copy() {
        return new Fleshtaker(this);
    }
}
