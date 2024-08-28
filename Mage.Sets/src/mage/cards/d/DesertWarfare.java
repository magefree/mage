package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.DesertsYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SandWarriorToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class DesertWarfare extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    private static final FilterCard filter1 = new FilterCard();

    static {
        filter1.add(SubType.DESERT.getPredicate());
    }

    public DesertWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you sacrifice a Desert and whenever a Desert card is put into your
        // graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step.

        //Based on Seraph, Yuma, Proud Protector
        Effect effect = new CreateDelayedTriggeredAbilityEffect((DelayedTriggeredAbility) new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DesertWarfareReturnEffect(), TargetController.YOU).setTriggerPhrase("Put that card onto the battlefield under your control at the beginning of your next end step.").setRuleVisible(false));

        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD, effect, filter, TargetController.YOU, SetTargetPointer.PERMANENT, false).setTriggerPhrase("Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step.").setRuleVisible(false));

        this.addAbility(new PutCardIntoGraveFromHandLibraryAllTriggeredAbility(Zone.BATTLEFIELD, effect,
                false, TargetController.YOU, SetTargetPointer.CARD).setTriggerPhrase("Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step."));

        //At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.
        //Based on Palani's Hatcher
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new DesertWarfareCreateTokensEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 5, true),
                "At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.").addHint(DesertsYouControlHint.instance));

    }

    private DesertWarfare(final DesertWarfare card) {
        super(card);
    }

    @Override
    public DesertWarfare copy() {
        return new DesertWarfare(this);
    }

}

//Based on PutCardIntoGraveFromAnywhereAllTriggeredAbility
class PutCardIntoGraveFromHandLibraryAllTriggeredAbility extends TriggeredAbilityImpl {

    private static FilterCard filterCard = new FilterCard();

    static {
        filterCard.add(SubType.DESERT.getPredicate());
    }

    private final SetTargetPointer setTargetPointer;

    public PutCardIntoGraveFromHandLibraryAllTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        FilterCard filter = filterCard.copy();
        this.setTargetPointer = setTargetPointer;
        filter.add(targetController.getOwnerPredicate());
    }

    protected PutCardIntoGraveFromHandLibraryAllTriggeredAbility(final PutCardIntoGraveFromHandLibraryAllTriggeredAbility ability) {
        super(ability);
        filterCard = filterCard.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public PutCardIntoGraveFromHandLibraryAllTriggeredAbility copy() {
        return new PutCardIntoGraveFromHandLibraryAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && zone.match(game.getState().getZone(getSourceId()))
                && ((ZoneChangeEvent) event).getFromZone().match(Zone.LIBRARY)
                && card != null
                && filterCard.match(card, getControllerId(), this, game)) {
            this.getEffects().setTargetPointer(new FixedTarget(card, game));
            return true;
        } else if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && zone.match(game.getState().getZone(getSourceId()))
                && ((ZoneChangeEvent) event).getFromZone().match(Zone.HAND)
                && card != null
                && filterCard.match(card, getControllerId(), this, game)) {
            this.getEffects().setTargetPointer(new FixedTarget(card, game));
            return true;
        }
        return false;
    }
}

class DesertWarfareReturnEffect extends OneShotEffect {

    DesertWarfareReturnEffect() {
        super(Outcome.Benefit);
    }

    private DesertWarfareReturnEffect(final DesertWarfareReturnEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareReturnEffect copy() {
        return new DesertWarfareReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }
}

class DesertWarfareCreateTokensEffect extends OneShotEffect {

    DesertWarfareCreateTokensEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.";
    }

    private DesertWarfareCreateTokensEffect(final DesertWarfareCreateTokensEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareCreateTokensEffect copy() {
        return new DesertWarfareCreateTokensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        int deserts = game.getBattlefield().countAll(new FilterLandPermanent(SubType.DESERT, "Deserts"), game.getControllerId(source.getSourceId()), game);
        Token token = new SandWarriorToken();
        token.putOntoBattlefield(deserts, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom)
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}