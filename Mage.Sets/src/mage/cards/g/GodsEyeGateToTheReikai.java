package mage.cards.g;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GodsEyeGateToTheReikai extends CardImpl {

    public GodsEyeGateToTheReikai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // When Gods' Eye, Gate to the Reikai is put into a graveyard from the battlefield, create a 1/1 colorless Spirit creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new CreateTokenEffect(new SpiritToken(), 1)));
    }

    private GodsEyeGateToTheReikai(final GodsEyeGateToTheReikai card) {
        super(card);
    }

    @Override
    public GodsEyeGateToTheReikai copy() {
        return new GodsEyeGateToTheReikai(this);
    }
}
