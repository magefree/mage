
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
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
 * @author nantuko
 */
public final class GrandAbolisher extends CardImpl {

    public GrandAbolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrandAbolisherEffect()));
    }

    private GrandAbolisher(final GrandAbolisher card) {
        super(card);
    }

    @Override
    public GrandAbolisher copy() {
        return new GrandAbolisher(this);
    }
}

class GrandAbolisherEffect extends ContinuousRuleModifyingEffectImpl {

    public GrandAbolisherEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments";        
    }

    public GrandAbolisherEffect(final GrandAbolisherEffect effect) {
        super(effect);        
    }

    @Override
    public GrandAbolisherEffect copy() {
        return new GrandAbolisherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        MageObject mageObject = game.getObject(source);
        if (activePlayer != null && mageObject != null) {
            return "You can't cast spells or activate abilities of artifacts, creatures, or enchantments during the turns of " + activePlayer.getLogName() +
                    " (" + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.isActivePlayer(source.getControllerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            switch(event.getType()) {
                case CAST_SPELL:
                    return true;
                case ACTIVATE_ABILITY:
                    Permanent permanent = game.getPermanent(event.getSourceId());
                    if (permanent != null) {
                        return permanent.isArtifact(game) || permanent.isCreature(game)
                                || permanent.isEnchantment(game);
                    }            
            }   
        }
        return false;
    }
}
