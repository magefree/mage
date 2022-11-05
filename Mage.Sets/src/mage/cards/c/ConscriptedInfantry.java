package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierArtifactToken;

/**
 *
 * @author weirddan455
 */
public final class ConscriptedInfantry extends CardImpl {

    public ConscriptedInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Conscripted Infantry dies, create a 1/1 colorless Soldier artifact creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SoldierArtifactToken())));
    }

    private ConscriptedInfantry(final ConscriptedInfantry card) {
        super(card);
    }

    @Override
    public ConscriptedInfantry copy() {
        return new ConscriptedInfantry(this);
    }
}
