package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonkClass extends CardImpl {

    public MonkClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // The second spell you cast each turn costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 1),
                MonkClassCondition.instance, "the second spell you cast each turn costs {1} less to cast"
        )), new SpellsCastWatcher());

        // {W}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{W}{U}"));

        // When this Class becomes level 2, return up to one target nonland permanent to its owner's hand.
        Ability ability = new BecomesClassLevelTriggeredAbility(new ReturnToHandTargetEffect(), 2);
        ability.addTarget(new TargetNonlandPermanent(0, 1, false));
        this.addAbility(ability);

        // {1}{W}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{1}{W}{U}"));

        // At the beginning of your upkeep, exile the top card of your library. For as long as it remains exiled, it has "You may cast this card from exile as long as you've cast another spell this turn."
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new BeginningOfUpkeepTriggeredAbility(
                        new MonkClassEffect(), TargetController.YOU, false
                ), 3
        )));
    }

    private MonkClass(final MonkClass card) {
        super(card);
    }

    @Override
    public MonkClass copy() {
        return new MonkClass(this);
    }
}

enum MonkClassCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher.getSpellsCastThisTurn(source.getControllerId()).size() == 1;
    }
}

class MonkClassEffect extends OneShotEffect {

    MonkClassEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. For as long as it remains exiled, " +
                "it has \"You may cast this card from exile as long as you've cast another spell this turn.\"";
    }

    private MonkClassEffect(final MonkClassEffect effect) {
        super(effect);
    }

    @Override
    public MonkClassEffect copy() {
        return new MonkClassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        game.addEffect(new GainAbilityTargetEffect(
                new SimpleStaticAbility(Zone.EXILED, new MonkClassCastEffect()),
                Duration.Custom, null, true
        ).setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class MonkClassCastEffect extends AsThoughEffectImpl {

    MonkClassCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "you may cast this card from exile as long as you've cast another spell this turn";
    }

    private MonkClassCastEffect(final MonkClassCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID mainCardId = CardUtil.getMainCardId(game, sourceId);
        if (!mainCardId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return card != null && watcher != null && !card.isLand(game)
                && watcher.getSpellsCastThisTurn(affectedControllerId).size() > 0;
    }

    @Override
    public MonkClassCastEffect copy() {
        return new MonkClassCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
