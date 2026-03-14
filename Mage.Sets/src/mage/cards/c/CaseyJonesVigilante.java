package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import java.util.UUID;

/**
 * @author muz
 */
public final class CaseyJonesVigilante extends CardImpl {

    public CaseyJonesVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Casey Jones enters, draw three cards. At the beginning of your next upkeep, discard three cards at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
            new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new DiscardControllerEffect(3, true))
        ));
        this.addAbility(ability);
    }

    private CaseyJonesVigilante(final CaseyJonesVigilante card) {
        super(card);
    }

    @Override
    public CaseyJonesVigilante copy() {
        return new CaseyJonesVigilante(this);
    }
}
