package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShinryuTranscendentRival extends CardImpl {

    public ShinryuTranscendentRival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As this creature transforms into Shinryu, choose an opponent.
        this.addAbility(new SimpleStaticAbility(new ShinryuTranscendentRivalEffect()));

        // Burning Chains -- When the chosen player loses the game, you win the game.
        this.addAbility(new ShinryuTranscendentRivalTriggeredAbility());
    }

    private ShinryuTranscendentRival(final ShinryuTranscendentRival card) {
        super(card);
    }

    @Override
    public ShinryuTranscendentRival copy() {
        return new ShinryuTranscendentRival(this);
    }
}

class ShinryuTranscendentRivalEffect extends ReplacementEffectImpl {

    ShinryuTranscendentRivalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as this creature transforms into {this}, choose an opponent";
    }

    private ShinryuTranscendentRivalEffect(final ShinryuTranscendentRivalEffect effect) {
        super(effect);
    }

    @Override
    public ShinryuTranscendentRivalEffect copy() {
        return new ShinryuTranscendentRivalEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        TargetPlayer target = new TargetOpponent(true);
        controller.choose(outcome, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
        game.getState().setValue(permanent.getId() + "_" + permanent.getZoneChangeCounter(game) + "_opponent", opponent.getId());
        permanent.addInfo("chosen opponent", CardUtil.addToolTipMarkTags("Chosen Opponent " + opponent.getLogName()), game);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMING;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId())
                && source.getSourcePermanentIfItStillExists(game) != null;
    }
}

class ShinryuTranscendentRivalTriggeredAbility extends TriggeredAbilityImpl {

    ShinryuTranscendentRivalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WinGameSourceControllerEffect());
        this.setTriggerPhrase("When the chosen player loses the game, ");
        this.withFlavorWord("Burning Chains");
    }

    private ShinryuTranscendentRivalTriggeredAbility(final ShinryuTranscendentRivalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShinryuTranscendentRivalTriggeredAbility copy() {
        return new ShinryuTranscendentRivalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int zcc = game.getState().getZoneChangeCounter(this.getSourceId());
        return Optional
                .of(this.getSourceId() + "_" + zcc + "_opponent")
                .map(game.getState()::getValue)
                .map(event.getPlayerId()::equals)
                .orElse(false);
    }
}
