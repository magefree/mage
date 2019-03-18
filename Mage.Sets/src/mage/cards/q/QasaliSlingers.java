
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class QasaliSlingers extends CardImpl {

    public QasaliSlingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Qasali Slingers or another Cat enters the battlefield under your control, you may destroy target artifact or enchantment.
        this.addAbility(new QasaliSlingersTriggeredAbility());
    }

    public QasaliSlingers(final QasaliSlingers card) {
        super(card);
    }

    @Override
    public QasaliSlingers copy() {
        return new QasaliSlingers(this);
    }
}

class QasaliSlingersTriggeredAbility extends TriggeredAbilityImpl {

    public QasaliSlingersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
        this.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    public QasaliSlingersTriggeredAbility(final QasaliSlingersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QasaliSlingersTriggeredAbility copy() {
        return new QasaliSlingersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.getId().equals(this.getSourceId())) {
                return true;
            }
            if (permanent.hasSubtype(SubType.CAT, game) && permanent.isControlledBy(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Cat enters the battlefield under your control, you may destroy target artifact or enchantment.";
    }
}
