package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarscapeCleric extends CardImpl {

    public StarscapeCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Offspring {2}{B}
        this.addAbility(new OffspringAbility("{2}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever you gain life, each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new LoseLifeOpponentsEffect(1)));
    }

    private StarscapeCleric(final StarscapeCleric card) {
        super(card);
    }

    @Override
    public StarscapeCleric copy() {
        return new StarscapeCleric(this);
    }
}
