package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SusurianVoidborn extends CardImpl {

    public SusurianVoidborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature or another creature or artifact you control dies, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
                new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Warp {B}
        this.addAbility(new WarpAbility(this, "{B}"));

    }

    private SusurianVoidborn(final SusurianVoidborn card) {
        super(card);
    }

    @Override
    public SusurianVoidborn copy() {
        return new SusurianVoidborn(this);
    }
}
