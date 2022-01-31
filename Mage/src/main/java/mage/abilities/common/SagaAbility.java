package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.SagaChapter;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.Targets;
import mage.util.CardUtil;

import java.util.Arrays;

/**
 * @author LevelX2
 */
public class SagaAbility extends SimpleStaticAbility {

    private final SagaChapter maxChapter;

    public SagaAbility(Card card) {
        this(card, SagaChapter.CHAPTER_III);
    }

    public SagaAbility(Card card, SagaChapter maxChapter) {
        super(Zone.ALL, new AddCountersSourceEffect(CounterType.LORE.createInstance()));
        this.maxChapter = maxChapter;
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()));
        ability.setRuleVisible(false);
        card.addAbility(ability);
    }

    public SagaAbility(final SagaAbility ability) {
        super(ability);
        this.maxChapter = ability.maxChapter;
    }

    public void addChapterEffect(Card card, SagaChapter chapter, Effect... effects) {
        addChapterEffect(card, chapter, chapter, new Effects(effects));
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

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effect effect, Target target, boolean optional) {
        addChapterEffect(card, fromChapter, toChapter, new Effects(effect), new Targets(target), optional);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects, Target target) {
        addChapterEffect(card, fromChapter, toChapter, effects, new Targets(target));
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects, Targets targets) {
        addChapterEffect(card, fromChapter, toChapter, effects, targets, false);
    }

    public void addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effects effects, Targets targets, boolean optional, Mode... modes) {
        for (int i = fromChapter.getNumber(); i <= toChapter.getNumber(); i++) {
            ChapterTriggeredAbility ability = new ChapterTriggeredAbility(null, SagaChapter.getChapter(i), toChapter, optional);
            for (Effect effect : effects) {
                if (effect != null) {
                    ability.addEffect(effect.copy());
                }
            }
            for (Target target : targets) {
                if (target != null) {
                    ability.addTarget(target.copy());
                }
            }
            for (Mode mode : modes) {
                ability.addMode(mode.copy());
            }
            if (i > fromChapter.getNumber()) {
                ability.setRuleVisible(false);
            }
            card.addAbility(ability);
        }
    }

    public SagaChapter getMaxChapter() {
        return maxChapter;
    }

    @Override
    public String getRule() {
        return "<i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after " + maxChapter.toString() + ".)</i> ";
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
}

class ChapterTriggeredAbility extends TriggeredAbilityImpl {

    private final SagaChapter chapterFrom, chapterTo;

    public ChapterTriggeredAbility(Effect effect, SagaChapter chapterFrom, SagaChapter chapterTo, boolean optional) {
        super(Zone.ALL, effect, optional);
        this.chapterFrom = chapterFrom;
        this.chapterTo = chapterTo;
    }

    public ChapterTriggeredAbility(final ChapterTriggeredAbility ability) {
        super(ability);
        this.chapterFrom = ability.chapterFrom;
        this.chapterTo = ability.chapterTo;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
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
