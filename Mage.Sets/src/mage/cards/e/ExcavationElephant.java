
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class ExcavationElephant extends CardImpl {

    public ExcavationElephant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // When Excavation Elephant enters the battlefield, if it was kicked, return target artifact card from your graveyard to your hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard")));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability, KickedCondition.instance,
                "When {this} enters the battlefield, if it was kicked, "
                + "return target artifact card from your graveyard to your hand."
        ));
    }

    private ExcavationElephant(final ExcavationElephant card) {
        super(card);
    }

    @Override
    public ExcavationElephant copy() {
        return new ExcavationElephant(this);
    }
}
