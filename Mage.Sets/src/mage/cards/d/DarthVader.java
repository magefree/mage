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
package mage.cards.d;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class DarthVader extends CardImpl {

    public DarthVader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Sith");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Darth Vader attacks, creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader.
        this.addAbility(new AttacksTriggeredAbility(new UnboostCreaturesDefendingPlayerEffect(), false, null, SetTargetPointer.PLAYER));
    }

    public DarthVader(final DarthVader card) {
        super(card);
    }

    @Override
    public DarthVader copy() {
        return new DarthVader(this);
    }
}

class UnboostCreaturesDefendingPlayerEffect extends ContinuousEffectImpl {

    public UnboostCreaturesDefendingPlayerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader";
    }

    public UnboostCreaturesDefendingPlayerEffect(final UnboostCreaturesDefendingPlayerEffect effect) {
        super(effect);
    }

    @Override
    public UnboostCreaturesDefendingPlayerEffect copy() {
        return new UnboostCreaturesDefendingPlayerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                int unboostCount = -1 * new CountersSourceCount(CounterType.P1P1).calculate(game, source, this);
                permanent.addPower(unboostCount);
                permanent.addToughness(unboostCount);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
