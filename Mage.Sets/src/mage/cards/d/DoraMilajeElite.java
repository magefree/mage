package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.VibraniumToken;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class DoraMilajeElite extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);
    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent("legendary permanents you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DoraMilajeElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When this creature enters, if an opponent controls more lands than you, create a tapped Vibranium token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new VibraniumToken(), 1, true), false
        ).withInterveningIf(condition));

        // Sacrifice this creature: Legendary permanents you control gain indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(),
                Duration.EndOfTurn,
                filter
            ),
            new SacrificeSourceCost()
        ));
    }

    private DoraMilajeElite(final DoraMilajeElite card) {
        super(card);
    }

    @Override
    public DoraMilajeElite copy() {
        return new DoraMilajeElite(this);
    }
}
