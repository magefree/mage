package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author muz
 */
public final class TeamTransmitter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HERO);

    public TeamTransmitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a Hero you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            new GainLifeEffect(1),
            filter
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private TeamTransmitter(final TeamTransmitter card) {
        super(card);
    }

    @Override
    public TeamTransmitter copy() {
        return new TeamTransmitter(this);
    }
}
