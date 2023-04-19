package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.PhyrexianSaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightsowerThallid extends CardImpl {

    public BlightsowerThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // When this creature transforms into Blightsower Thallid or dies, create a 1/1 green Phyrexian Saproling creature token.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new PhyrexianSaprolingToken()), false,
                "When this creature transforms into {this} or dies, ",
                new TransformIntoSourceTriggeredAbility(null),
                new DiesSourceTriggeredAbility(null, false)
        ));
    }

    private BlightsowerThallid(final BlightsowerThallid card) {
        super(card);
    }

    @Override
    public BlightsowerThallid copy() {
        return new BlightsowerThallid(this);
    }
}
