
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author FenrisulfrX
 */
public final class PhyrexianTotem extends CardImpl {

    public PhyrexianTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {2}{B}: {this} becomes a 5/5 black Horror artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new PhyrexianTotemToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}")));
        // Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents.
        this.addAbility(new PhyrexianTotemTriggeredAbility());
    }

    private PhyrexianTotem(final PhyrexianTotem card) {
        super(card);
    }

    @Override
    public PhyrexianTotem copy() {
        return new PhyrexianTotem(this);
    }
    
    private static class PhyrexianTotemToken extends TokenImpl {
        PhyrexianTotemToken() {
            super("Phyrexian Horror", "5/5 black Phyrexian Horror artifact creature with trample");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlack(true);
            this.subtype.add(SubType.PHYREXIAN);
            this.subtype.add(SubType.HORROR);
            power = new MageInt(5);
            toughness = new MageInt(5);
            this.addAbility(TrampleAbility.getInstance());
        }
        public PhyrexianTotemToken(final PhyrexianTotemToken token) {
            super(token);
        }

        public PhyrexianTotemToken copy() {
            return new PhyrexianTotemToken(this);
        }
    }
}

class PhyrexianTotemTriggeredAbility extends TriggeredAbilityImpl {
    
    public PhyrexianTotemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterControlledPermanent(), 0,""));
    }
    
    public PhyrexianTotemTriggeredAbility(final PhyrexianTotemTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PhyrexianTotemTriggeredAbility copy() {
        return new PhyrexianTotemTriggeredAbility(this);
    }
    
    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (permanent != null) {
            return permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(getControllerId()));
            ((SacrificeEffect) getEffects().get(0)).setAmount(StaticValue.get(event.getAmount()));
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents.";
    }
}