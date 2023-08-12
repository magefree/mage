package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenerousEnt extends CardImpl {

    public GenerousEnt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Generous Ent enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Forestcycling {1}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private GenerousEnt(final GenerousEnt card) {
        super(card);
    }

    @Override
    public GenerousEnt copy() {
        return new GenerousEnt(this);
    }
}
