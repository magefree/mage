package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrchardStrider extends CardImpl {

    public OrchardStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // When Orchard Strider enters the battlefield, create two Food tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 2)));

        // Basic landcycling {1}{G}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private OrchardStrider(final OrchardStrider card) {
        super(card);
    }

    @Override
    public OrchardStrider copy() {
        return new OrchardStrider(this);
    }
}
