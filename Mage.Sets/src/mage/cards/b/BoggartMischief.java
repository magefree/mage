package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.BlackAndRedGoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoggartMischief extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.GOBLIN, "a Goblin creature you control");

    public BoggartMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);

        // When this enchantment enters, you may blight 1. If you do, create two 1/1 black and red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new BlackAndRedGoblinToken(), 2), new BlightCost(1))
        ));

        // Whenever a Goblin creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private BoggartMischief(final BoggartMischief card) {
        super(card);
    }

    @Override
    public BoggartMischief copy() {
        return new BoggartMischief(this);
    }
}
