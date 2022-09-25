package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrElenoraTheDiscerning extends CardImpl {

    public SyrElenoraTheDiscerning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Syr Elenora the Discerning's power is equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(CardsInControllerHandCount.instance, Duration.EndOfGame)
        ));

        // When Syr Elenora enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Spells your opponents cast that target Syr Elenora cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(2, new FilterCard("Spells"), TargetController.OPPONENT))
        );
    }

    private SyrElenoraTheDiscerning(final SyrElenoraTheDiscerning card) {
        super(card);
    }

    @Override
    public SyrElenoraTheDiscerning copy() {
        return new SyrElenoraTheDiscerning(this);
    }
}
