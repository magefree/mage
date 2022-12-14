package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPermanentBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.Set;
import java.util.UUID;

public class BenSolo extends CardImpl {
    public BenSolo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        //Whenever Ben Solo is dealt damage, it deals that much damage to target player or planeswalker.
        this.addAbility(new BenSoloTriggeredAbility());
    }

    public BenSolo(CardImpl card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new BenSolo(this);
    }
}

class BenSoloTriggeredAbility extends TriggeredAbilityImpl {

    UUID benSoloID;

    BenSoloTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BenSoloEffect(), false);
        this.addTarget(new TargetPlayerOrPlaneswalker());
        benSoloID = this.getSourceId();
    }

    private BenSoloTriggeredAbility(final BenSoloTriggeredAbility ability) {
        super(ability);
        benSoloID = ability.benSoloID;
    }

    @Override
    public BenSoloTriggeredAbility copy() {
        return new BenSoloTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (benSoloID == null) {
            benSoloID = this.getSourceId();
            if (benSoloID == null) {
                return false;
            }
        }

        int damage = 0;
        DamagedPermanentBatchEvent dEvent = (DamagedPermanentBatchEvent) event;
        Set<DamagedEvent> set = dEvent.getEvents();
        for (DamagedEvent damagedEvent : set) {
            UUID targetID = damagedEvent.getTargetId();
            if (targetID == null) {
                continue;
            }

            if (targetID == benSoloID) {
                damage += damagedEvent.getAmount();
            }
        }

        if (damage > 0) {
            this.getEffects().setValue("damage", damage);
            this.getEffects().setValue("benSoloID", benSoloID);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Ben Solo is dealt damage, it deals that much damage to target player or planeswalker.";
    }
}

class BenSoloEffect extends OneShotEffect {

    BenSoloEffect() {
        super(Outcome.Benefit);
    }

    private BenSoloEffect(final BenSoloEffect effect) {
        super(effect);
    }

    @Override
    public BenSoloEffect copy() {
        return new BenSoloEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer)getValue("damage");
        UUID benSoloID = (UUID)getValue("benSoloID");

        if (benSoloID == null || damage == null || damage < 1) {
            return false;
        }

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damage, benSoloID, source, game);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, benSoloID, source, game);
            return true;
        }
        return false;
    }
}
