package mage.cards.i;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InnocuousInsect extends CardImpl {

    public InnocuousInsect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Buyback {1}{U}
        this.addAbility(new BuybackAbility(new ManaCostsImpl<>("{1}{U}")));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you cast this spell, draw a card.
        this.addAbility(new CastSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private InnocuousInsect(final InnocuousInsect card) {
        super(card);
    }

    @Override
    public InnocuousInsect copy() {
        return new InnocuousInsect(this);
    }
}