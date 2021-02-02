
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author Styxo
 */
public final class KraumLudevicsOpus extends CardImpl {

    public KraumLudevicsOpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever an opponent casts their second spell each turn, draw a card.
        this.addAbility(new KraumLudevicsOpusTriggeredAbility(), new CastSpellLastTurnWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());

    }

    private KraumLudevicsOpus(final KraumLudevicsOpus card) {
        super(card);
    }

    @Override
    public KraumLudevicsOpus copy() {
        return new KraumLudevicsOpus(this);
    }
}

class KraumLudevicsOpusTriggeredAbility extends TriggeredAbilityImpl {

    public KraumLudevicsOpusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    public KraumLudevicsOpusTriggeredAbility(final KraumLudevicsOpusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KraumLudevicsOpusTriggeredAbility copy() {
        return new KraumLudevicsOpusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their second spell each turn, draw a card.";
    }
}
