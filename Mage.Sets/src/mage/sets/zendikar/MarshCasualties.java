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
package mage.sets.zendikar;

import java.util.Iterator;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.keyword.KickerAbility;

/**
 *
 * @author North
 */
public class MarshCasualties extends CardImpl {

    private static final String ruleText = "Creatures target player controls get -1/-1 until end of turn. If {this} was kicked, those creatures get -2/-2 until end of turn instead";

    public MarshCasualties(UUID ownerId) {
        super(ownerId, 101, "Marsh Casualties", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}{B}");
        this.expansionSetCode = "ZEN";

        this.color.setBlack(true);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Creatures target player controls get -1/-1 until end of turn. If Marsh Casualties was kicked, those creatures get -2/-2 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new MarshCasualtiesEffect(-2, -2),
                new MarshCasualtiesEffect(-1, -1),
                KickedCondition.getInstance(),
                ruleText));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public MarshCasualties(final MarshCasualties card) {
        super(card);
    }

    @Override
    public MarshCasualties copy() {
        return new MarshCasualties(this);
    }
}

class MarshCasualtiesEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public MarshCasualtiesEffect(int power, int toughness) {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
    }

    public MarshCasualtiesEffect(final MarshCasualtiesEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public MarshCasualtiesEffect copy() {
        return new MarshCasualtiesEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getFirstTarget(), game)) {
                affectedObjectList.add(new MageObjectReference(creature));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(power);
                permanent.addToughness(toughness);
            } else {
                it.remove();
            }
        }
        return true;
    }
}