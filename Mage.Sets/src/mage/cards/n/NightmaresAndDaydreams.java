package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightmaresAndDaydreams extends CardImpl {

    public NightmaresAndDaydreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Until your next turn, whenever you cast an instant or sorcery spell, target player mills cards equal to that spell's mana value.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new NightmaresAndDaydreamsTriggeredAbility())
        );

        // IV -- Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(3), new DrawCardSourceControllerEffect(1),
                        NightmaresAndDaydreamsCondition.instance, "draw a card. " +
                        "If a graveyard has twenty or more cards in it, draw three cards instead"
                )
        );
        this.addAbility(sagaAbility);
    }

    private NightmaresAndDaydreams(final NightmaresAndDaydreams card) {
        super(card);
    }

    @Override
    public NightmaresAndDaydreams copy() {
        return new NightmaresAndDaydreams(this);
    }
}

enum NightmaresAndDaydreamsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .anyMatch(player -> player.getGraveyard().size() >= 20);
    }
}

class NightmaresAndDaydreamsTriggeredAbility extends DelayedTriggeredAbility {

    NightmaresAndDaydreamsTriggeredAbility() {
        super(new MillCardsTargetEffect(SavedDamageValue.MANY)
                        .setText("target player mills cards equal to that spell's mana value"),
                Duration.UntilYourNextTurn, false, false);
        this.setTriggerPhrase("Until your next turn, whenever you cast an instant or sorcery spell, ");
        this.addTarget(new TargetPlayer());
    }

    private NightmaresAndDaydreamsTriggeredAbility(final NightmaresAndDaydreamsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NightmaresAndDaydreamsTriggeredAbility copy() {
        return new NightmaresAndDaydreamsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isControlledBy(getControllerId()) || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().setValue("damage", spell.getManaValue());
        return true;
    }
}
