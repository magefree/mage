
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LevelX2
 */
public final class HallowedSpiritkeeper extends CardImpl {

    public HallowedSpiritkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Hallowed Spiritkeeper dies, create X 1/1 white Spirit creature tokens with flying, where X is the number of creature cards in your graveyard.
        Effect effect = new CreateTokenEffect(new SpiritWhiteToken(), new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards")));
        effect.setText("create X 1/1 white Spirit creature tokens with flying, where X is the number of creature cards in your graveyard");
        this.addAbility(new DiesSourceTriggeredAbility(effect, false));

    }

    private HallowedSpiritkeeper(final HallowedSpiritkeeper card) {
        super(card);
    }

    @Override
    public HallowedSpiritkeeper copy() {
        return new HallowedSpiritkeeper(this);
    }
}
