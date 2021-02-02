
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author caldover
 */
public final class GoringCeratops extends CardImpl {

    public GoringCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double Strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Goring Ceratops attacks, other creatures you control gain double strike until end of turn.
        Effect effect = new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true);
        effect.setText("other creatures you control gain double strike until end of turn");
        Ability ability = new AttacksTriggeredAbility(effect, false);
        this.addAbility(ability);
    }

    private GoringCeratops(final GoringCeratops card) {
        super(card);
    }

    @Override
    public GoringCeratops copy() {
        return new GoringCeratops(this);
    }
}
