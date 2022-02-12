package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DemonBerserkerToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBloodskyMassacre extends CardImpl {

    public TheBloodskyMassacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create a 2/3 red Demon Berserker creature token with menace.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new DemonBerserkerToken()));

        // II — Whenever a Berserker attacks this turn, you draw a card and you lose 1 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateDelayedTriggeredAbilityEffect(new TheBloodskyMassacreAbility()));

        // III — Add {R} for each Berserker you control. Until end of turn, you don't lose this mana as steps or phases end.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheBloodskyMassacreEffect());

        this.addAbility(sagaAbility);
    }

    private TheBloodskyMassacre(final TheBloodskyMassacre card) {
        super(card);
    }

    @Override
    public TheBloodskyMassacre copy() {
        return new TheBloodskyMassacre(this);
    }
}

class TheBloodskyMassacreAbility extends DelayedTriggeredAbility {

    TheBloodskyMassacreAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
        this.addEffect(new LoseLifeSourceControllerEffect(1));
    }

    private TheBloodskyMassacreAbility(final TheBloodskyMassacreAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && permanent.hasSubtype(SubType.BERSERKER, game);
    }

    @Override
    public TheBloodskyMassacreAbility copy() {
        return new TheBloodskyMassacreAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Berserker attacks this turn, you draw a card and you lose 1 life.";
    }
}

class TheBloodskyMassacreEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BERSERKER);

    TheBloodskyMassacreEffect() {
        super(Outcome.Benefit);
        staticText = "add {R} for each Berserker you control. Until end of turn, " +
                "you don't lose this mana as steps and phases end";
    }

    private TheBloodskyMassacreEffect(final TheBloodskyMassacreEffect effect) {
        super(effect);
    }

    @Override
    public TheBloodskyMassacreEffect copy() {
        return new TheBloodskyMassacreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int berserkers = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        player.getManaPool().addMana(new Mana(ManaType.RED, berserkers), game, source, true);
        return true;
    }
}
