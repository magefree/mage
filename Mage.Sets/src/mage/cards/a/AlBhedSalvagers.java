package mage.cards.a;

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
public final class AlBhedSalvagers extends CardImpl {

    public AlBhedSalvagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever this creature or another creature or artifact you control dies, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
                new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AlBhedSalvagers(final AlBhedSalvagers card) {
        super(card);
    }

    @Override
    public AlBhedSalvagers copy() {
        return new AlBhedSalvagers(this);
    }
}
