package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class VraanExecutionerThane extends CardImpl {

    public VraanExecutionerThane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more other creatures you control die, each opponent loses 2 life and you gain 2 life. This ability triggers only once each turn.
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(2), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setTriggerPhrase("Whenever one or more other creatures you control die, ").setTriggersOnceEachTurn(true);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private VraanExecutionerThane(final VraanExecutionerThane card) {
        super(card);
    }

    @Override
    public VraanExecutionerThane copy() {
        return new VraanExecutionerThane(this);
    }
}
