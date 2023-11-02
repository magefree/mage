
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author magenoxx_at_gmail.com
 */
public final class ElderscaleWurm extends CardImpl {

    public ElderscaleWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Elderscale Wurm enters the battlefield, if your life total is less than 7, your life total becomes 7.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ElderscaleWurmSetLifeEffect(), false));

        // As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ElderscaleWurmReplacementEffect()));
    }

    private ElderscaleWurm(final ElderscaleWurm card) {
        super(card);
    }

    @Override
    public ElderscaleWurm copy() {
        return new ElderscaleWurm(this);
    }
}

class ElderscaleWurmSetLifeEffect extends OneShotEffect {

    public ElderscaleWurmSetLifeEffect() {
        super(Outcome.Benefit);
        this.staticText = "if your life total is less than 7, your life total becomes 7";
    }

    private ElderscaleWurmSetLifeEffect(final ElderscaleWurmSetLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player != null && player.getLife() < 7) {
            player.setLife(7, game, source);
        }

        return true;
    }

    @Override
    public ElderscaleWurmSetLifeEffect copy() {
        return new ElderscaleWurmSetLifeEffect(this);
    }

}

class ElderscaleWurmReplacementEffect extends ReplacementEffectImpl {

    public ElderscaleWurmReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead";
    }

    private ElderscaleWurmReplacementEffect(final ElderscaleWurmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ElderscaleWurmReplacementEffect copy() {
        return new ElderscaleWurmReplacementEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && controller.getLife() >= 7
                    && (controller.getLife() - event.getAmount()) < 7) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            event.setAmount(controller.getLife() - 7);
        }
        return false;
    }

}
