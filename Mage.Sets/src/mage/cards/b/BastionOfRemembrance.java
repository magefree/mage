package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BastionOfRemembrance extends CardImpl {

    public BastionOfRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When Bastion of Remembrance enters the battlefield, create a 1/1 white Human Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));

        // Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private BastionOfRemembrance(final BastionOfRemembrance card) {
        super(card);
    }

    @Override
    public BastionOfRemembrance copy() {
        return new BastionOfRemembrance(this);
    }
}
