package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author TheElk801
 */
public final class KraulForagers extends CardImpl {

    public KraulForagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Undergrowth — When Kraul Foragers enters the battlefield, you gain 1 life for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(new CardsInControllerGraveyardCount(new FilterCreatureCard())),
                false, "<i>Undergrowth</i> &mdash; "
        ));
    }

    public KraulForagers(final KraulForagers card) {
        super(card);
    }

    @Override
    public KraulForagers copy() {
        return new KraulForagers(this);
    }
}
