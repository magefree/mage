package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author amoscatelli
 */
public final class AlandraSkyDreamer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("drakes you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.DRAKE.getPredicate());
    }
    
    public AlandraSkyDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card earch turn, create a 2/2 blue Drake creature token with flying.
        this.addAbility(
                new DrawCardTriggeredAbility(
                        new CreateTokenEffect(
                                new DrakeToken()
                        ),
                        false,
                        2
                )
        );
        
        // Whenever you draw your fifth card each turn, Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand.
        this.addAbility(
                new DrawCardTriggeredAbility(
                        new BoostAllOfChosenSubtypeEffect(
                                CardsInControllerHandCount.instance, 
                                CardsInControllerHandCount.instance, 
                                Duration.EndOfTurn, 
                                filter, 
                                true
                        ),
                        false,
                        5
                )
        );
        
        // Whenever you draw your fifth card each turn, Alandra, Sky Dreamer get +X/+X until end of turn, where X is the number of cards in your hand.
        this.addAbility(
                new DrawCardTriggeredAbility(
                        new BoostSourceEffect(
                                CardsInControllerHandCount.instance, 
                                CardsInControllerHandCount.instance, 
                                Duration.EndOfTurn
                        ),
                        false,
                        5
                )
        );
        
    }

    private AlandraSkyDreamer(final AlandraSkyDreamer card) {
        super(card);
    }

    @Override
    public AlandraSkyDreamer copy() {
        return new AlandraSkyDreamer(this);
    }
}
