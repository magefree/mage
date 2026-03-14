package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterPakku extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON, "Lesson cards"), null);

    public MasterPakku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Master Pakku becomes tapped, target player mills X cards, where X is the number of Lesson cards in your graveyard.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new MillCardsTargetEffect(xValue));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.addHint(LessonsInGraveCondition.getHint()));
    }

    private MasterPakku(final MasterPakku card) {
        super(card);
    }

    @Override
    public MasterPakku copy() {
        return new MasterPakku(this);
    }
}
