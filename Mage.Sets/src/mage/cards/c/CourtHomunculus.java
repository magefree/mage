
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class CourtHomunculus extends CardImpl {

    public CourtHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HOMUNCULUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Court Homunculus gets +1/+1 as long as you control another artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new ControlsAnotherArtifactCondition(), "{this} gets +1/+1 as long as you control another artifact")));
    }

    private CourtHomunculus(final CourtHomunculus card) {
        super(card);
    }

    @Override
    public CourtHomunculus copy() {
        return new CourtHomunculus(this);
    }
}

class ControlsAnotherArtifactCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> controlledArtifacts = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), game);
        for (Permanent permanent : controlledArtifacts) {
            if (!permanent.getId().equals(game.getObject(source).getId())) {
                return true;
            }
        }
        return false;
    }
}