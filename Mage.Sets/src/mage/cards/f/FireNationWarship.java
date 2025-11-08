package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationWarship extends CardImpl {

    public FireNationWarship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this Vehicle dies, create a Clue token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private FireNationWarship(final FireNationWarship card) {
        super(card);
    }

    @Override
    public FireNationWarship copy() {
        return new FireNationWarship(this);
    }
}
