package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class InspiredSphinx extends CardImpl {

    public InspiredSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Inspired Sphinx enters the battlefield, draw cards equal to the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(OpponentsCount.instance).setText("draw cards equal to the number of opponents you have")
        ));

        // {3}{U}: Create a colorless 1/1 Thopter artifact creature token with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken()), new ManaCostsImpl<>("{3}{U}")));
    }

    private InspiredSphinx(final InspiredSphinx card) {
        super(card);
    }

    @Override
    public InspiredSphinx copy() {
        return new InspiredSphinx(this);
    }
}
