

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SkymarkRoc extends CardImpl {

    public SkymarkRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skymark Roc attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.
        this.addAbility(new SkymarkRocAbility());
    }

    private SkymarkRoc(final SkymarkRoc card) {
        super(card);
    }

    @Override
    public SkymarkRoc copy() {
        return new SkymarkRoc(this);
    }
}

class SkymarkRocAbility extends TriggeredAbilityImpl {

    public SkymarkRocAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    private SkymarkRocAbility(final SkymarkRocAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls with toughness 2 or less");
            UUID defenderId = game.getCombat().getDefendingPlayerId(sourceId, game);
            filter.add(new ControllerIdPredicate(defenderId));
            filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.";
    }

    @Override
    public SkymarkRocAbility copy() {
        return new SkymarkRocAbility(this);
    }
}