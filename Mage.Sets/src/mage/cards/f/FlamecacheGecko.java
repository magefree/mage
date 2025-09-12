package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamecacheGecko extends CardImpl {

    public FlamecacheGecko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Flamecache Gecko enters, if an opponent lost life this turn, add {B}{R}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BasicManaEffect(new Mana(0, 0, 1, 1, 0, 0, 0, 0))
        ).withInterveningIf(OpponentsLostLifeCondition.instance));

        // {1}{R}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private FlamecacheGecko(final FlamecacheGecko card) {
        super(card);
    }

    @Override
    public FlamecacheGecko copy() {
        return new FlamecacheGecko(this);
    }
}
