
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbt33, jeffwadsworth (Archmage Ascension)
 */
public final class ObstinateFamiliar extends CardImpl {

    public ObstinateFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If you would draw a card, you may skip that draw instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ObstinateFamiliarReplacementEffect()));
    }

    private ObstinateFamiliar(final ObstinateFamiliar card) {
        super(card);
    }

    @Override
    public ObstinateFamiliar copy() {
        return new ObstinateFamiliar(this);
    }
}

class ObstinateFamiliarReplacementEffect extends ReplacementEffectImpl {

    public ObstinateFamiliarReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may skip that draw instead";
    }

    private ObstinateFamiliarReplacementEffect(final ObstinateFamiliarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ObstinateFamiliarReplacementEffect copy() {
        return new ObstinateFamiliarReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null && you.chooseUse(Outcome.AIDontUseIt, "Skip this draw?", source, game)){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }   
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent archmage = game.getPermanent(source.getSourceId());
        if (archmage != null && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
    
}
