
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ManicScribe extends CardImpl {

    public ManicScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Manic Scribe enters the battlefield, each opponent puts the top three cards of their library into their graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutTopCardOfLibraryIntoGraveEachPlayerEffect(3, TargetController.OPPONENT), false));

        // <i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // that player puts the top three cards of their library into their graveyard.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PutTopCardOfLibraryIntoGraveTargetEffect(3), TargetController.OPPONENT, false, true),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                        + "that player puts the top three cards of their library into their graveyard."));
    }

    public ManicScribe(final ManicScribe card) {
        super(card);
    }

    @Override
    public ManicScribe copy() {
        return new ManicScribe(this);
    }
}
