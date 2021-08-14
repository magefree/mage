package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RoninWarclub extends CardImpl {

    public RoninWarclub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);
        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1)));

        // Whenever a creature enters the battlefield under your control, attach Ronin Warclub to that creature.
        Ability ability = new RoninWarclubTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Equip {5} ({5}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5)));
    }

    private RoninWarclub(final RoninWarclub card) {
        super(card);
    }

    @Override
    public RoninWarclub copy() {
        return new RoninWarclub(this);
    }

    private class RoninWarclubTriggeredAbility extends TriggeredAbilityImpl {

        public RoninWarclubTriggeredAbility() {
            super(Zone.BATTLEFIELD, new RoninWarclubAttachEffect(), false);
        }

        public RoninWarclubTriggeredAbility(RoninWarclubTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null
                    && permanent.isCreature(game)
                    && permanent.isControlledBy(this.controllerId)) {

                if (!this.getTargets().isEmpty()) {
                    // remove previous target
                    if (!this.getTargets().get(0).getTargets().isEmpty()) {
                        this.getTargets().clear();
                        this.addTarget(new TargetCreaturePermanent());
                    }
                    Target target = this.getTargets().get(0);
                    if (target instanceof TargetCreaturePermanent) {
                        target.add(event.getTargetId(), game);
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public RoninWarclubTriggeredAbility copy() {
            return new RoninWarclubTriggeredAbility(this);
        }
    }

    private static class RoninWarclubAttachEffect extends OneShotEffect {

        public RoninWarclubAttachEffect() {
            super(Outcome.BoostCreature);
            this.staticText = "Whenever a creature enters the battlefield under your control, attach {this} to that creature";
        }

        public RoninWarclubAttachEffect(final RoninWarclubAttachEffect effect) {
            super(effect);
        }

        @Override
        public RoninWarclubAttachEffect copy() {
            return new RoninWarclubAttachEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            Permanent attachment = game.getPermanent(source.getSourceId());
            if (permanent != null && attachment != null) {
                if (attachment.getAttachedTo() != null) {
                    Permanent oldTarget = game.getPermanent(attachment.getAttachedTo());
                    if (oldTarget != null) {
                        oldTarget.removeAttachment(source.getSourceId(), source, game);
                    }
                }
                boolean result;
                result = permanent.addAttachment(source.getSourceId(), source, game);
                return result;
            }
            return false;
        }
    }

}
