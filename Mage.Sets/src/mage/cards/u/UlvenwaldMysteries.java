
package mage.cards.u;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.HumanSoldierToken;

/**
 *
 * @author fireshoes
 */
public final class UlvenwaldMysteries extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature you control");
    private static final FilterControlledPermanent filterClue = new FilterControlledPermanent("a Clue");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
        filterClue.add(SubType.CLUE.getPredicate());
    }

    public UlvenwaldMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a nontoken creature you control dies, investigate. <i>(Create a colorless Clue artifact token with "{2}, Sacrifice this artifact: Draw a card.")</i>
        this.addAbility(new DiesCreatureTriggeredAbility(new InvestigateEffect(), false, filter));

        // Whenever you sacrifice a Clue, create a 1/1 white Human Soldier creature token.
        this.addAbility(new UlvenwaldMysteriesTriggeredAbility());
    }

    private UlvenwaldMysteries(final UlvenwaldMysteries card) {
        super(card);
    }

    @Override
    public UlvenwaldMysteries copy() {
        return new UlvenwaldMysteries(this);
    }
}

class UlvenwaldMysteriesTriggeredAbility extends TriggeredAbilityImpl {

    public UlvenwaldMysteriesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanSoldierToken()));
        setLeavesTheBattlefieldTrigger(true);
    }

    public UlvenwaldMysteriesTriggeredAbility(final UlvenwaldMysteriesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UlvenwaldMysteriesTriggeredAbility copy() {
        return new UlvenwaldMysteriesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.CLUE, game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you sacrifice a Clue, " ;
    }
}