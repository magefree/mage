package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoostedSloop extends CardImpl {

    public BoostedSloop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you attack, draw a card, then discard a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), 1
        ));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private BoostedSloop(final BoostedSloop card) {
        super(card);
    }

    @Override
    public BoostedSloop copy() {
        return new BoostedSloop(this);
    }
}
