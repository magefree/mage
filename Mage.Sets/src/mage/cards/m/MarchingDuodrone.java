package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchingDuodrone extends CardImpl {

    public MarchingDuodrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Marching Duodrone attacks, each player creates a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenAllEffect(
                new TreasureToken(), TargetController.EACH_PLAYER
        )));
    }

    private MarchingDuodrone(final MarchingDuodrone card) {
        super(card);
    }

    @Override
    public MarchingDuodrone copy() {
        return new MarchingDuodrone(this);
    }
}
