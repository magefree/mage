package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;

/**
 * @author amoscatelli
 */
public final class AlandraSkyDreamer extends CardImpl {

    public AlandraSkyDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card earch turn, create a 2/2 blue Drake creature token with flying.
        this.addAbility(
                new DrawNthCardTriggeredAbility(
                        new CreateTokenEffect(
                                new DrakeToken()
                        ),
                        false,
                        2
                )
        );

        // Whenever you draw your fifth card each turn, Alandra, Sky Dreamer and Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(
                Predicates.or(
                        SubType.DRAKE.getPredicate(),
                        new CardIdPredicate(
                                getId()
                        )
                )
        );
        this.addAbility(
                new DrawNthCardTriggeredAbility(
                        new BoostControlledEffect(
                                CardsInControllerHandCount.instance,
                                CardsInControllerHandCount.instance,
                                Duration.EndOfTurn,
                                filter,
                                false
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
