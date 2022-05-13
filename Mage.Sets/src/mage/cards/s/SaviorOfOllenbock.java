package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaviorOfOllenbock extends CardImpl {

    public SaviorOfOllenbock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Training
        this.addAbility(new TrainingAbility());

        // Whenever Savior of Ollenbock trains, exile up to one other target creature from the battlefield or creature card from a graveyard.
        this.addAbility(new SaviorOfOllenbockTriggeredAbility());

        // When Savior of Ollenbock leaves the battlefield, put the exiled cards onto the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SaviorOfOllenbockEffect(), false));
    }

    private SaviorOfOllenbock(final SaviorOfOllenbock card) {
        super(card);
    }

    @Override
    public SaviorOfOllenbock copy() {
        return new SaviorOfOllenbock(this);
    }
}

class SaviorOfOllenbockTriggeredAbility extends TriggeredAbilityImpl {

    SaviorOfOllenbockTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetForSourceEffect());
        this.addTarget(new TargetCardInGraveyardBattlefieldOrStack(
                0, 1,
                StaticFilters.FILTER_CARD_CREATURE,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
    }

    private SaviorOfOllenbockTriggeredAbility(final SaviorOfOllenbockTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SaviorOfOllenbockTriggeredAbility copy() {
        return new SaviorOfOllenbockTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRAINED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} trains, exile up to one other target " +
                "creature from the battlefield or creature card from a graveyard.";
    }
}

class SaviorOfOllenbockEffect extends OneShotEffect {

    SaviorOfOllenbockEffect() {
        super(Outcome.Benefit);
        staticText = "put the exiled cards onto the battlefield under their owners' control";
    }

    private SaviorOfOllenbockEffect(final SaviorOfOllenbockEffect effect) {
        super(effect);
    }

    @Override
    public SaviorOfOllenbockEffect copy() {
        return new SaviorOfOllenbockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -1));
        return player != null && exileZone != null && !exileZone.isEmpty() && player.moveCards(
                exileZone.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
    }
}
