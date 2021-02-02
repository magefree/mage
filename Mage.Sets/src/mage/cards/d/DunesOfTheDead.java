package mage.cards.d;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class DunesOfTheDead extends CardImpl {

    public DunesOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        addAbility(new ColorlessManaAbility());

        // When Dunes of the Dead is put into a graveyard from the battlefield, create a 2/2 black Zombie creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 1)));
    }

    private DunesOfTheDead(final DunesOfTheDead card) {
        super(card);
    }

    @Override
    public DunesOfTheDead copy() {
        return new DunesOfTheDead(this);
    }
}
