package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.ForestTentacleToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VerdantKraken extends CardImpl {

    public VerdantKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of each player's upkeep, you create a 3/3 green Forest Tentacle land creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            TargetController.EACH_PLAYER,
            new CreateTokenEffect(new ForestTentacleToken()),
            false
        ));
    }

    private VerdantKraken(final VerdantKraken card) {
        super(card);
    }

    @Override
    public VerdantKraken copy() {
        return new VerdantKraken(this);
    }
}
