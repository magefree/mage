
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class PonybackBrigade extends CardImpl {

    public PonybackBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ponyback Brigade enters the battlefield or is turned face up, create three 1/1 red Goblin creature tokens.
        this.addAbility(new PonybackBrigadeAbility(new GoblinToken()));

        // Morph {2}{R}{W}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{R}{W}{B}")));
    }

    private PonybackBrigade(final PonybackBrigade card) {
        super(card);
    }

    @Override
    public PonybackBrigade copy() {
        return new PonybackBrigade(this);
    }
}

class PonybackBrigadeAbility extends TriggeredAbilityImpl {

    public PonybackBrigadeAbility(Token token) {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(token, 3), false);
        this.setWorksFaceDown(true);
    }

    public PonybackBrigadeAbility(final PonybackBrigadeAbility ability) {
        super(ability);
    }

    @Override
    public PonybackBrigadeAbility copy() {
        return new PonybackBrigadeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TURNEDFACEUP && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When {this} enters the battlefield or is turned face up, " ;
    }
}
