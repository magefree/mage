
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TidalForce extends CardImpl {

    public TidalForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each upkeep, you may tap or untap target permanent.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MayTapOrUntapTargetEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private TidalForce(final TidalForce card) {
        super(card);
    }

    @Override
    public TidalForce copy() {
        return new TidalForce(this);
    }
}
