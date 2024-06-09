package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilentSubmersible extends CardImpl {

    public SilentSubmersible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Silent Submersible deals combat damage to a player or planeswalker, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private SilentSubmersible(final SilentSubmersible card) {
        super(card);
    }

    @Override
    public SilentSubmersible copy() {
        return new SilentSubmersible(this);
    }
}
