package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaraldUnitesTheElves extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF);

    public HaraldUnitesTheElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Mill three cards. You may put an Elf or Tyvar card from your graveyard onto the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new HaraldUnitesTheElvesEffect()
        );

        // II — Put a +1/+1 counter on each Elf you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        );

        // III — Whenever an Elf you control attacks this turn, target creature an opponent controls gets -1/-1 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new HaraldUnitesTheElvesTriggeredAbility())
        );

        this.addAbility(sagaAbility);
    }

    private HaraldUnitesTheElves(final HaraldUnitesTheElves card) {
        super(card);
    }

    @Override
    public HaraldUnitesTheElves copy() {
        return new HaraldUnitesTheElves(this);
    }
}

class HaraldUnitesTheElvesEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Elf or Tyvar card from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.ELF.getPredicate(),
                SubType.TYVAR.getPredicate()
        ));
    }

    HaraldUnitesTheElvesEffect() {
        super(Outcome.Benefit);
        staticText = "Mill three cards. You may put an Elf or Tyvar card from your graveyard onto the battlefield.";
    }

    private HaraldUnitesTheElvesEffect(final HaraldUnitesTheElvesEffect effect) {
        super(effect);
    }

    @Override
    public HaraldUnitesTheElvesEffect copy() {
        return new HaraldUnitesTheElvesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        TargetCard targetCard = new TargetCardInYourGraveyard(0, 1, filter, true);
        player.choose(outcome, targetCard, source, game);
        Card card = player.getGraveyard().get(targetCard.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}

class HaraldUnitesTheElvesTriggeredAbility extends DelayedTriggeredAbility {

    HaraldUnitesTheElvesTriggeredAbility() {
        super(new BoostTargetEffect(-1, -1), Duration.EndOfTurn, false);
        this.addTarget(new TargetOpponentsCreaturePermanent());
    }

    private HaraldUnitesTheElvesTriggeredAbility(final HaraldUnitesTheElvesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HaraldUnitesTheElvesTriggeredAbility copy() {
        return new HaraldUnitesTheElvesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(getControllerId())
                && permanent.hasSubtype(SubType.ELF, game);
    }

    @Override
    public String getRule() {
        return "Whenever an Elf you control attacks this turn, " +
                "target creature an opponent controls gets -1/-1 until end of turn.";
    }
}
