package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarrenWarleader extends CardImpl {

    public WarrenWarleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // Whenever you attack, choose one --
        // * Create a 1/1 white Rabbit creature token that's tapped and attacking.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new RabbitToken(), 1, true, true), 1
        );

        // * Attacking creatures you control get +1/+1 until end of turn.
        ability.addEffect(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        this.addAbility(ability);
    }

    private WarrenWarleader(final WarrenWarleader card) {
        super(card);
    }

    @Override
    public WarrenWarleader copy() {
        return new WarrenWarleader(this);
    }
}
