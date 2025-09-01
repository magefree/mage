package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReadAheadAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author LevelX2
 */
public class SagaAbility extends SimpleStaticAbility {

    private final SagaChapter maxChapter;
    private boolean showSacText;
    private final boolean readAhead;

    public SagaAbility(Card card) {
        this(card, SagaChapter.CHAPTER_III, false);
    }

    public SagaAbility(Card card, SagaChapter maxChapter) {
        this(card, maxChapter, false);
    }

    public SagaAbility(Card card, SagaChapter maxChapter, boolean readAhead) {
        super(Zone.ALL, null);
        this.maxChapter = maxChapter;
        this.showSacText = card.getSecondCardFace() == null && !card.isNightCard();
        this.readAhead = readAhead;
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);
        Ability ability = new EntersBattlefieldAbility(new SagaLoreCountersEffect(maxChapter));
        ability.setRuleVisible(false);
        card.addAbility(ability);
        if (readAhead) {
            card.addAbility(ReadAheadAbility.getInstance());
        }
    }

    protected SagaAbility(final SagaAbility ability) {
        super(ability);
        this.maxChapter = ability.maxChapter;
        this.showSacText = ability.showSacText;
        this.readAhead = ability.readAhead;
    }

    public void addChapterEffect(Card card, SagaChapter chapter, Effect... effects) {
        addChapterEffect(card, chapter, chapter, new Effects(effects));
    }

    public void addChapterEffect(Card card, SagaChapter chapter, Effect effect, Target target) {
        addChapterEffect(card, chapter, chapter, new Effects(effect), target);
    }

    public void addChapterEffect(Card card, SagaChapter chapter, Effects effects, Target target) {
        addChapterEffect(card, chapter, chapter, effects, target);
    }

    public void addChapterEffect(Card card, SagaChapter chapter, Consumer<TriggeredAbility> applier) {
        addChapterEffect(card, chapter, chapter, applier);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effect effect) {
        addChapterEffect(card, fromChapter, toChapter, new Effects(effect), (Target) null);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects) {
        addChapterEffect(card, fromChapter, toChapter, effects, (Target) null);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effect effect, Target target) {
        addChapterEffect(card, fromChapter, toChapter, effect, target, false);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects, Target target) {
        addChapterEffect(card, fromChapter, toChapter, effects, target, false);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effect effect, Target target, boolean optional) {
        addChapterEffect(card, fromChapter, toChapter, new Effects(effect), target, optional);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Consumer<TriggeredAbility> applier) {
        addChapterEffect(card, fromChapter, toChapter, false, applier);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects, Target target, boolean optional) {
        addChapterEffect(card, fromChapter, toChapter, optional, ability -> {
            for (Effect effect : effects) {
                if (effect != null) {
                    ability.addEffect(effect.copy());
                }
            }
            if (target != null) {
                ability.addTarget(target.copy());
            }
        });
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, boolean optional, Consumer<TriggeredAbility> applier) {
        for (int i = fromChapter.getNumber(); i <= toChapter.getNumber(); i++) {
            ChapterTriggeredAbility ability = new ChapterTriggeredAbility(
                    SagaChapter.getChapter(i), toChapter, optional
            );
            applier.accept(ability);
            if (i > fromChapter.getNumber()) {
                ability.setRuleVisible(false);
            }
            card.addAbility(ability);
        }
    }

    public SagaChapter getMaxChapter() {
        return maxChapter;
    }

    public SagaAbility withShowSacText(boolean showSacText) {
        this.showSacText = showSacText;
        return this;
    }

    @Override
    public String getRule() {
        return (readAhead
                ? "<i>(Choose a chapter and start with that many lore counters. " +
                "Add one after your draw step. Skipped chapters don't trigger."
                : "<i>(As this Saga enters and after your draw step, add a lore counter.")
                + (showSacText ? " Sacrifice after " + maxChapter.toString() + '.' : "") + ")</i> ";
    }

    @Override
    public SagaAbility copy() {
        return new SagaAbility(this);
    }

    public static boolean isChapterAbility(StackObject stackObject) {
        if (stackObject instanceof StackAbility) {
            return stackObject.getStackAbility() instanceof ChapterTriggeredAbility;
        }
        return false;
    }

    public static boolean isChapterAbility(TriggeredAbility ability) {
        return ability instanceof ChapterTriggeredAbility;
    }

    public static boolean isFinalAbility(Ability ability, int maxChapter) {
        return ability instanceof ChapterTriggeredAbility
                && ((ChapterTriggeredAbility) ability).getChapterFrom().getNumber() == maxChapter;
    }

    public static Ability makeGainReadAheadAbility() {
        return new GainReadAheadAbility();
    }
}

class SagaLoreCountersEffect extends OneShotEffect {

    private final SagaChapter maxChapter;

    SagaLoreCountersEffect(SagaChapter maxChapter) {
        super(Outcome.Benefit);
        this.maxChapter = maxChapter;
    }

    private SagaLoreCountersEffect(final SagaLoreCountersEffect effect) {
        super(effect);
        this.maxChapter = effect.maxChapter;
    }

    @Override
    public SagaLoreCountersEffect copy() {
        return new SagaLoreCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (!permanent.hasAbility(ReadAheadAbility.getInstance(), game)
                && !GainReadAheadAbility.checkForAbility(game, source)) {
            return permanent.addCounters(CounterType.LORE.createInstance(), source, game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int counters = player.getAmount(
                1, maxChapter.getNumber(),
                "Choose the number of lore counters to enter with", source, game
        );
        return permanent.addCounters(CounterType.LORE.createInstance(counters), source, game);
    }
}

class ChapterTriggeredAbility extends TriggeredAbilityImpl {

    private final SagaChapter chapterFrom, chapterTo;

    ChapterTriggeredAbility(SagaChapter chapterFrom, SagaChapter chapterTo, boolean optional) {
        super(Zone.ALL, null, optional);
        this.chapterFrom = chapterFrom;
        this.chapterTo = chapterTo;
    }

    private ChapterTriggeredAbility(final ChapterTriggeredAbility ability) {
        super(ability);
        this.chapterFrom = ability.chapterFrom;
        this.chapterTo = ability.chapterTo;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())
                || !event.getData().equals(CounterType.LORE.getName())) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int loreCounters = permanent.getCounters(game).getCount(CounterType.LORE);
        if (permanent.getTurnsOnBattlefield() == 0
                && (permanent.hasAbility(ReadAheadAbility.getInstance(), game)
                || GainReadAheadAbility.checkForAbility(game, this))) {
            return chapterFrom.getNumber() == loreCounters;
        }
        return loreCounters - event.getAmount() < chapterFrom.getNumber()
                && chapterFrom.getNumber() <= loreCounters;
    }

    @Override
    public ChapterTriggeredAbility copy() {
        return new ChapterTriggeredAbility(this);
    }

    public String getChapterRule() {
        String rule = super.getRule();
        return Character.toUpperCase(rule.charAt(0)) + rule.substring(1);
    }

    public SagaChapter getChapterFrom() {
        return chapterFrom;
    }

    public SagaChapter getChapterTo() {
        return chapterTo;
    }

    @Override
    public String getRule() {
        return Arrays
                .stream(SagaChapter.values())
                .filter(chapter -> chapterFrom.getNumber() <= chapter.getNumber())
                .filter(chapter -> chapter.getNumber() <= chapterTo.getNumber())
                .map(SagaChapter::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("")
                + " - " + CardUtil.getTextWithFirstCharUpperCase(super.getRule());
    }
}

class GainReadAheadAbility extends SimpleStaticAbility {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SAGA, "Sagas");

    GainReadAheadAbility() {
        super(new GainAbilityControlledEffect(
                ReadAheadAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("Sagas you control have read ahead. <i>(As a Saga enters, choose a chapter " +
                "and start with that many lore counters. Skipped chapters don't trigger.)</i>"));
    }

    private GainReadAheadAbility(final GainReadAheadAbility ability) {
        super(ability);
    }

    @Override
    public GainReadAheadAbility copy() {
        return new GainReadAheadAbility(this);
    }

    static boolean checkForAbility(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        source.getControllerId(), source, game
                )
                .stream()
                .anyMatch(p -> p.getAbilities(game).containsClass(GainReadAheadAbility.class));
    }
}
