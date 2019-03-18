
package mage.cards.g;

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
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class GhituChronicler extends CardImpl {

    public GhituChronicler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Kicker {3}{R}
        this.addAbility(new KickerAbility("{3}{R}"));

        // When Ghitu Chronicler enters the battlefield, if it was kicked, return target instant or sorcery card from your graveyard to your hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard")));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability, KickedCondition.instance,
                "When {this} enters the battlefield, if it was kicked, "
                + "return target instant or sorcery card from your graveyard to your hand."
        ));
    }

    public GhituChronicler(final GhituChronicler card) {
        super(card);
    }

    @Override
    public GhituChronicler copy() {
        return new GhituChronicler(this);
    }
}
