
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AncestorsChosen extends CardImpl {

    public AncestorsChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new CardsInControllerGraveyardCount())
                .setText("you gain 1 life for each card in your graveyard"), false));
    }

    private AncestorsChosen(final AncestorsChosen card) {
        super(card);
    }

    @Override
    public AncestorsChosen copy() {
        return new AncestorsChosen(this);
    }
}
