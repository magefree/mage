package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SynodCenturion extends CardImpl {

    public SynodCenturion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When you control no other artifacts, sacrifice Synod Centurion.
        this.addAbility(new SynodCenturionStateTriggeredAbility());
    }

    private SynodCenturion(final SynodCenturion card) {
        super(card);
    }

    @Override
    public SynodCenturion copy() {
        return new SynodCenturion(this);
    }

}

class SynodCenturionStateTriggeredAbility extends StateTriggeredAbility {

    SynodCenturionStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        setTriggerPhrase("When you control no other artifacts, ");
    }

    private SynodCenturionStateTriggeredAbility(final SynodCenturionStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SynodCenturionStateTriggeredAbility copy() {
        return new SynodCenturionStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (!perm.getId().equals(this.getSourceId()) && perm.isArtifact(game)) {
                return false;
            }
        }
        return true;
    }

}
