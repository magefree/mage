package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AprilReporterOfTheWeird extends CardImpl {

    public AprilReporterOfTheWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever April deals combat damage to a player, draw that many cards, then discard a card.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false, false
        );
        ability.addEffect(new DiscardControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private AprilReporterOfTheWeird(final AprilReporterOfTheWeird card) {
        super(card);
    }

    @Override
    public AprilReporterOfTheWeird copy() {
        return new AprilReporterOfTheWeird(this);
    }
}
