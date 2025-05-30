package mage.cards.c;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.SavedDiscardValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CryptcallerChariot extends CardImpl {

    public CryptcallerChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you discard one or more cards, create that many tapped 2/2 black Zombie creature tokens.
        this.addAbility(new DiscardOneOrMoreCardsTriggeredAbility(new CreateTokenEffect(
                new ZombieToken(), SavedDiscardValue.MANY, true, false
        )));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private CryptcallerChariot(final CryptcallerChariot card) {
        super(card);
    }

    @Override
    public CryptcallerChariot copy() {
        return new CryptcallerChariot(this);
    }
}
