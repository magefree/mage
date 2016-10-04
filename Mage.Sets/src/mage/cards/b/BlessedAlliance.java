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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.PlayerPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class BlessedAlliance extends CardImpl {

    private static final FilterPlayer filterSacrifice = new FilterPlayer("opponent to sacrifice an attacking creature");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creatures to untap");
    private static final FilterPlayer filterGainLife = new FilterPlayer("player to gain life");

    static {
        filterSacrifice.add(new PlayerPredicate(TargetController.OPPONENT));
    }

    public BlessedAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Escalate {2}
        this.addAbility(new EscalateAbility(new GenericManaCost(2)));

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target player gains 4 life.
        Effect effect = new GainLifeTargetEffect(4);
        effect.setText("Target player gains 4 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterGainLife));

        // Untap up to two target creatures.
        Mode mode = new Mode();
        effect = new UntapTargetEffect();
        effect.setText("Untap up to two target creatures");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(0, 2, filterCreature, false));
        this.getSpellAbility().addMode(mode);

        // Target opponent sacrifices an attacking creature.
        mode = new Mode();
        mode.getEffects().add(new SacrificeEffect(new FilterAttackingCreature(), 1, "Target opponent"));
        mode.getTargets().add(new TargetPlayer(1, 1, false, filterSacrifice));
        this.getSpellAbility().addMode(mode);
    }

    public BlessedAlliance(final BlessedAlliance card) {
        super(card);
    }

    @Override
    public BlessedAlliance copy() {
        return new BlessedAlliance(this);
    }
}
