
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author BetaSteward
 */
public final class WerewolfRansacker extends CardImpl {

    public WerewolfRansacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.
        this.addAbility(new WerewolfRansackerAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf Ransacker.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public WerewolfRansacker(final WerewolfRansacker card) {
        super(card);
    }

    @Override
    public WerewolfRansacker copy() {
        return new WerewolfRansacker(this);
    }
}

class WerewolfRansackerAbility extends TriggeredAbilityImpl {

    static final String RULE_TEXT = "Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller";

    public WerewolfRansackerAbility() {
        super(Zone.BATTLEFIELD, new WerewolfRansackerEffect(), true);
        this.addTarget(new TargetArtifactPermanent());
    }

    public WerewolfRansackerAbility(final WerewolfRansackerAbility ability) {
        super(ability);
    }

    @Override
    public WerewolfRansackerAbility copy() {
        return new WerewolfRansackerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return RULE_TEXT + '.';
    }
}

class WerewolfRansackerEffect extends OneShotEffect {

    public WerewolfRansackerEffect() {
        super(Outcome.DestroyPermanent);
    }

    public WerewolfRansackerEffect(final WerewolfRansackerEffect effect) {
        super(effect);
    }

    @Override
    public WerewolfRansackerEffect copy() {
        return new WerewolfRansackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (!targetPointer.getTargets(game, source).isEmpty()) {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.destroy(source.getSourceId(), game, false)) {
                        affectedTargets++;
                        if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                            Player player = game.getPlayer(permanent.getControllerId());
                            if (player != null) {
                                player.damage(3, source.getSourceId(), game, false, true);
                            }
                        }
                    }
                }
            }
        }
        return affectedTargets > 0;
    }
}
