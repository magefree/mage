/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.SagaChapter;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class SagaAbility extends TriggeredAbilityImpl {

    private SagaChapter maxChapter;

    public SagaAbility(Card card, SagaChapter maxChapter) {
        super(Zone.ALL, new AddCountersSourceEffect(CounterType.LORE.createInstance()), false);
        this.maxChapter = maxChapter;
        this.setRuleVisible(false);
        ((CardImpl) card).addAbility(new SagaAddCounterAbility(maxChapter));

    }

    public SagaAbility(final SagaAbility ability) {
        super(ability);
        this.maxChapter = ability.maxChapter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield, " + super.getRule();
    }

    public Ability addChapterEffect(Card card, SagaChapter chapter, Effect effect) {
        return addChapterEffect(card, chapter, chapter, effect);
    }

    public Ability addChapterEffect(Card card, SagaChapter fromChapter, SagaChapter toChapter, Effect effect) {
        ChapterTriggeredAbility ability = new ChapterTriggeredAbility(effect, fromChapter, toChapter);
        ((CardImpl) card).addAbility(ability);
        if (maxChapter == null || toChapter.getNumber() > maxChapter.getNumber()) {
            maxChapter = toChapter;
        }
        return ability;
    }

    public SagaChapter getMaxChapter() {
        return maxChapter;
    }

    @Override
    public SagaAbility copy() {
        return new SagaAbility(this);
    }

    public static boolean isChapterAbility(StackObject stackObject) {
        if (stackObject instanceof StackAbility) {
            return ((StackAbility) stackObject).getStackAbility() instanceof ChapterTriggeredAbility;
        }
        return false;
    }
}

class ChapterTriggeredAbility extends TriggeredAbilityImpl {

    SagaChapter chapterFrom, chapterTo;

    public ChapterTriggeredAbility(Effect effect, SagaChapter chapterFrom, SagaChapter chapterTo) {
        super(Zone.BATTLEFIELD, effect, false);
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
        return event.getType() == EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId()) && event.getData().equals(CounterType.LORE.getName())) {
            Permanent sourceSaga = game.getPermanentOrLKIBattlefield(getSourceId());
            if (sourceSaga != null) {
                int loreCounters = sourceSaga.getCounters(game).getCount(CounterType.LORE);
                return chapterFrom.getNumber() <= loreCounters
                        && chapterTo.getNumber() >= loreCounters;
            }
        }
        return false;
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
        StringBuilder sb = new StringBuilder();
        for (SagaChapter chapter : SagaChapter.values()) {
            if (chapter.getNumber() >= getChapterFrom().getNumber()
                    && chapter.getNumber() < getChapterTo().getNumber()) {
                sb.append(chapter.toString()).append(", ");
            } else if (chapter.equals(getChapterTo())) {
                sb.append(chapter.toString());
            }
        }
        String text = super.getRule();
        sb.append(": ").append(Character.toUpperCase(text.charAt(0))).append(text.substring(1));
        return sb.toString();
    }
}

class SagaAddCounterAbility extends TriggeredAbilityImpl {

    SagaChapter maxChapter;

    SagaAddCounterAbility(SagaChapter maxChapter) {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LORE.createInstance()), false);
        this.usesStack = false;
        this.maxChapter = maxChapter;
    }

    SagaAddCounterAbility(final SagaAddCounterAbility ability) {
        super(ability);
        this.maxChapter = ability.maxChapter;
    }

    @Override
    public SagaAddCounterAbility copy() {
        return new SagaAddCounterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "<i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after " + maxChapter.toString() + ".)</i> ";
    }

}
