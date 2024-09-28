package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CarrotCake extends CardImpl {

    public CarrotCake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.FOOD);

        // When Carrot Cake enters and when you sacrifice it, create a 1/1 white Rabbit creature token and scry 1.
        Ability ability = new OrTriggeredAbility(
                Zone.ALL,
                new CreateTokenEffect(new RabbitToken()),
                new EntersBattlefieldTriggeredAbility(null),
                new SacrificeSourceTriggeredAbility(null)
        ).setTriggerPhrase("When {this} enters and when you sacrifice it, ");
        ability.addEffect(new ScryEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Carrot Cake: You gain 3 life.
        this.addAbility(new FoodAbility(true));
    }

    private CarrotCake(final CarrotCake card) {
        super(card);
    }

    @Override
    public CarrotCake copy() {
        return new CarrotCake(this);
    }
}
