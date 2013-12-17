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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class EvilEyeOfOrmsByGore extends CardImpl<EvilEyeOfOrmsByGore> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Walls");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Wall")));
    }
    
    public EvilEyeOfOrmsByGore(UUID ownerId) {
        super(ownerId, 21, "Evil Eye of Orms-by-Gore", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Eye");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Non-Eye creatures you control can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EvilEyeOfOrmsByGoreEffect()));
        
        // Evil Eye of Orms-by-Gore can't be blocked except by Walls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    public EvilEyeOfOrmsByGore(final EvilEyeOfOrmsByGore card) {
        super(card);
    }

    @Override
    public EvilEyeOfOrmsByGore copy() {
        return new EvilEyeOfOrmsByGore(this);
    }
}

class EvilEyeOfOrmsByGoreEffect extends ReplacementEffectImpl<EvilEyeOfOrmsByGoreEffect> {

    public EvilEyeOfOrmsByGoreEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Non-Eye creatures you control can't attack";
    }

    public EvilEyeOfOrmsByGoreEffect(final EvilEyeOfOrmsByGoreEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EvilEyeOfOrmsByGoreEffect copy() {
        return new EvilEyeOfOrmsByGoreEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DECLARE_ATTACKER) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    if (!permanent.hasSubtype("Eye")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}