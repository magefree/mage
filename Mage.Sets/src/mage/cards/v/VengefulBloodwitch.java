package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulBloodwitch extends CardImpl {

    public VengefulBloodwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever this creature or another creature you control dies, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VengefulBloodwitch(final VengefulBloodwitch card) {
        super(card);
    }

    @Override
    public VengefulBloodwitch copy() {
        return new VengefulBloodwitch(this);
    }
}
