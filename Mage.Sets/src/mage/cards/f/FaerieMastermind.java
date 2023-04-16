package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

public class FaerieMastermind extends CardImpl {

    public FaerieMastermind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.addSubType(SubType.FAERIE);
        this.addSubType(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Flash
        this.addAbility(FlashAbility.getInstance());

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Whenever an opponent draws their second card each turn, you draw a card.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("you draw a card"),
                false, TargetController.OPPONENT, 2
        ));

        //{3}{U}: Each player draws a card.
        this.addAbility(new SimpleActivatedAbility(new DrawCardAllEffect(1), new ManaCostsImpl<>("{3}{U}")));
    }

    private FaerieMastermind(final FaerieMastermind card) {
        super(card);
    }

    @Override
    public FaerieMastermind copy() {
        return new FaerieMastermind(this);
    }
}
