package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DonatelloTurtleTechie extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledArtifactPermanent("you control an artifact")
    );

    public DonatelloTurtleTechie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Donatello enters, if you control an artifact, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition));
    }

    private DonatelloTurtleTechie(final DonatelloTurtleTechie card) {
        super(card);
    }

    @Override
    public DonatelloTurtleTechie copy() {
        return new DonatelloTurtleTechie(this);
    }
}
