package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class FeySteed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(
            "another target attacking creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public FeySteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{W}{W}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Fey Steed attacks, another target attacking creature you control
        // gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Whenever a creature or planeswalker you control becomes the target of a spell
        // or ability an opponent controls, you may draw a card.
        this.addAbility(new FeySteedTriggeredAbility());
    }

    private FeySteed(final FeySteed card) {
        super(card);
    }

    @Override
    public FeySteed copy() {
        return new FeySteed(this);
    }
}

class FeySteedTriggeredAbility extends TriggeredAbilityImpl {

    public FeySteedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public FeySteedTriggeredAbility(FeySteedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player targetter = game.getPlayer(event.getPlayerId());
        if (targetter == null || !targetter.hasOpponent(this.controllerId, game)) {
            return false;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isControlledBy(this.getControllerId())
                || (!permanent.isCreature(game) && !permanent.isPlaneswalker(game))) {
            return false;
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature or planeswalker you control becomes the target of a spell or ability an opponent controls, you may draw a card.";
    }

    @Override
    public FeySteedTriggeredAbility copy() {
        return new FeySteedTriggeredAbility(this);
    }
}
