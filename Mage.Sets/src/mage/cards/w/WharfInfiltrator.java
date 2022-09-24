
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.EldraziHorrorToken;

/**
 *
 * @author fireshoes
 */
public final class WharfInfiltrator extends CardImpl {

    public WharfInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Whenever Wharf Infiltrator deals combat damage to a player, you may draw a card. If you do, discard a card.
        Effect effect = new DrawDiscardControllerEffect();
        effect.setText("you may draw a card. If you do, discard a card");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(effect, true));

        // Whenever you discard a creature card, you may pay {2}. If you do, create a 3/2 colorless Eldrazi Horror creature token.
        effect = new CreateTokenEffect(new EldraziHorrorToken());
        effect.setText("create a 3/2 colorless Eldrazi Horror creature token");
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(effect, new GenericManaCost(2));
        this.addAbility(new WharfInfiltratorDiscardAbility(doIfCostPaid));
    }

    private WharfInfiltrator(final WharfInfiltrator card) {
        super(card);
    }

    @Override
    public WharfInfiltrator copy() {
        return new WharfInfiltrator(this);
    }
}

class WharfInfiltratorDiscardAbility extends TriggeredAbilityImpl {

    WharfInfiltratorDiscardAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you discard a creature card, " );
    }

    WharfInfiltratorDiscardAbility(final WharfInfiltratorDiscardAbility ability) {
        super(ability);
    }

    @Override
    public WharfInfiltratorDiscardAbility copy() {
        return new WharfInfiltratorDiscardAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        return isControlledBy(event.getPlayerId()) && card != null && card.isCreature(game);
    }
}
