package mage.cards.s;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShacklesOfTreachery extends CardImpl {

    public ShacklesOfTreachery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. Until end of turn, it gains haste and "Whenever this creature deals damage, destroy target Equipment attached to it."
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, it gains haste"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new ShacklesOfTreacheryTriggeredAbility(), Duration.EndOfTurn
        ).setText("and \"Whenever this creature deals damage, destroy target Equipment attached to it.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShacklesOfTreachery(final ShacklesOfTreachery card) {
        super(card);
    }

    @Override
    public ShacklesOfTreachery copy() {
        return new ShacklesOfTreachery(this);
    }
}

class ShacklesOfTreacheryTriggeredAbility extends TriggeredAbilityImpl {

    private enum ShacklesOfTreacheryPredicate implements ObjectSourcePlayerPredicate<MageObject> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
            Permanent permanent = input.getSource().getSourcePermanentIfItStillExists(game);
            return permanent != null && permanent.getAttachments().contains(input.getObject().getId());
        }
    }

    private static final FilterPermanent filter
            = new FilterEquipmentPermanent("Equipment attached to this creature");

    static {
        filter.add(ShacklesOfTreacheryPredicate.instance);
    }

    ShacklesOfTreacheryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
        this.addTarget(new TargetPermanent(filter));
    }

    private ShacklesOfTreacheryTriggeredAbility(final ShacklesOfTreacheryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShacklesOfTreacheryTriggeredAbility copy() {
        return new ShacklesOfTreacheryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals damage, destroy target Equipment attached to it.";
    }
}
