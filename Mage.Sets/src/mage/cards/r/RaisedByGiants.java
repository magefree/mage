package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaisedByGiants extends CardImpl {

    public RaisedByGiants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have base power and toughness 10/10 and are Giants in addition to their other types.
        Ability ability = new SimpleStaticAbility(new SetPowerToughnessAllEffect(
                10, 10, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER, true
        ));
        ability.addEffect(new AddCardSubtypeAllEffect(
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER,
                SubType.GIANT, null
        ).setText("and are Giants in addition to their other types"));
        this.addAbility(ability);
    }

    private RaisedByGiants(final RaisedByGiants card) {
        super(card);
    }

    @Override
    public RaisedByGiants copy() {
        return new RaisedByGiants(this);
    }
}
