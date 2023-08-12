
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.RebelStarshipToken;

/**
 *
 * @author Styxo
 */
public final class AdmiralAckbar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship creatures");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
    }

    public AdmiralAckbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CALAMARI);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cast Admiral Ackbar, create two 2/3 blue Rebel Starship artifact creature tokens with spaceflight name B-Wing.
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new RebelStarshipToken(), 2), false));

        // At the beginning of each upkeep, untap all starships you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new UntapAllControllerEffect(filter), TargetController.ANY, false));

        // Whenever two or more Starship creatures you control attack, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 2, filter));
    }

    private AdmiralAckbar(final AdmiralAckbar card) {
        super(card);
    }

    @Override
    public AdmiralAckbar copy() {
        return new AdmiralAckbar(this);
    }
}

class AdmiralAckbarTriggeredAbility extends TriggeredAbilityImpl {

    public AdmiralAckbarTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public AdmiralAckbarTriggeredAbility(final AdmiralAckbarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AdmiralAckbarTriggeredAbility copy() {
        return new AdmiralAckbarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 2 && game.getCombat().getAttackingPlayerId().equals(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever two or more Starship creatures you control attack, draw a card";
    }
}
