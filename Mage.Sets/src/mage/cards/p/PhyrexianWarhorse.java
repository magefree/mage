package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author weirddan455
 */
public final class PhyrexianWarhorse extends CardImpl {

    public PhyrexianWarhorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {W}
        this.addAbility(new KickerAbility("{W}"));

        // When Phyrexian Warhorse enters the battlefield, if it was kicked, create a 1/1 white Soldier creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken())),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, create a 1/1 white Soldier creature token."
        ));

        // {1}, Sacrifice another creature: Phyrexian Warhorse gets +2/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private PhyrexianWarhorse(final PhyrexianWarhorse card) {
        super(card);
    }

    @Override
    public PhyrexianWarhorse copy() {
        return new PhyrexianWarhorse(this);
    }
}
