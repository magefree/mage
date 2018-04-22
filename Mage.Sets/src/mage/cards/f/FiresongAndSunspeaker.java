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
package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.GainAbilitySpellsEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author rscoates
 */
public class FiresongAndSunspeaker extends CardImpl {

    private static final FilterObject filter = new FilterObject("Red instant and sorcery spells you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public FiresongAndSunspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Red instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilitySpellsEffect(LifelinkAbility.getInstance(), filter);
        effect.setText("Red instant and sorcery spells you control have lifelink");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Whenever a white instant or sorcery spell causes you to gain life, Firesong and Sunspeaker deals 3 damage to target creature or player.
        this.addAbility(new FiresongAndSunspeakerTriggeredAbility());
    }

    public FiresongAndSunspeaker(final FiresongAndSunspeaker card) {
        super(card);
    }

    @Override
    public FiresongAndSunspeaker copy() {
        return new FiresongAndSunspeaker(this);
    }
}

class FiresongAndSunspeakerTriggeredAbility extends TriggeredAbilityImpl {

    public FiresongAndSunspeakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
        this.addTarget(new TargetCreatureOrPlayer());
    }

    public FiresongAndSunspeakerTriggeredAbility(final FiresongAndSunspeakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FiresongAndSunspeakerTriggeredAbility copy() {
        return new FiresongAndSunspeakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object != null && object instanceof Spell) {
            if (object.getColor(game).contains(ObjectColor.WHITE)
                    && (object.isInstant()
                    || object.isSorcery())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a white instant or sorcery spell causes you to gain life, Firesong and Sunspeaker deals 3 damage to target creature or player.";
    }
}
