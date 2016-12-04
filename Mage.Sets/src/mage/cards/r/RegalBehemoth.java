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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class RegalBehemoth extends CardImpl {

    public RegalBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add("Lizard");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Regal Behemoth enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever you tap a land for mana while you're the monarch, add one mana of any color to your mana pool.
        ManaEffect manaEffect = new AddManaOfAnyColorEffect();
        manaEffect.setText("add one mana of any color to your mana pool <i>(in addition to the mana the land produces)</i>.");
        ManaEffect effect = manaEffect;
        this.addAbility(new RegalBehemothTriggeredManaAbility(
                effect, new FilterControlledLandPermanent("you tap a land")));
    }

    public RegalBehemoth(final RegalBehemoth card) {
        super(card);
    }

    @Override
    public RegalBehemoth copy() {
        return new RegalBehemoth(this);
    }
}

class RegalBehemothTriggeredManaAbility extends TriggeredManaAbility {

    private final FilterPermanent filter;

    public RegalBehemothTriggeredManaAbility(ManaEffect effect, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
    }

    public RegalBehemothTriggeredManaAbility(RegalBehemothTriggeredManaAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getControllerId().equals(game.getMonarchId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                ManaEvent mEvent = (ManaEvent) event;
                for (Effect effect : getEffects()) {
                    effect.setValue("mana", mEvent.getMana());
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RegalBehemothTriggeredManaAbility copy() {
        return new RegalBehemothTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a land for mana while you're the monarch, " + super.getRule();
    }
}
