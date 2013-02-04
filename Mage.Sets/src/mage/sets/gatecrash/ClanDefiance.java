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
package mage.sets.gatecrash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ClanDefiance extends CardImpl<ClanDefiance> {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static final private FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ClanDefiance(UUID ownerId) {
        super(ownerId, 151, "Clan Defiance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Choose one or more - Clan Defiance deals X damage to target creature with flying; Clan Defiance deals X damage to target creature without flying; and/or Clan Defiance deals X damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        Mode mode1 = new Mode();
        mode1.getEffects().add(new DamageTargetEffect(new ManacostVariableValue()));
        mode1.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().addMode(mode1);

        Mode mode2 = new Mode();
        mode2.getEffects().add(new DamageTargetEffect(new ManacostVariableValue()));
        mode2.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode2);

        Mode mode3 = new Mode();
        mode3.getEffects().add(new DamageTargetEffect(new ManacostVariableValue(), true, "target creature with flying, then deals X damage to target creature without flying"));
        mode3.getTargets().add(new TargetCreaturePermanent(filter));
        mode3.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().addMode(mode3);

        Mode mode4 = new Mode();
        mode4.getEffects().add(new DamageTargetEffect(new ManacostVariableValue(), true, "target creature with flying, then deals X damage to target player"));
        mode4.getTargets().add(new TargetCreaturePermanent(filter));
        mode4.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode4);

        Mode mode5 = new Mode();
        mode5.getEffects().add(new DamageTargetEffect(new ManacostVariableValue(), true, "target creature without flying, then deals X damage to target player"));
        mode5.getTargets().add(new TargetCreaturePermanent(filter2));
        mode5.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode5);
        
        Mode mode6 = new Mode();
        mode6.getEffects().add(new DamageTargetEffect(new ManacostVariableValue(), true, "target creature with flying, then deals X damage to target creature without flying, then deals X damage to target player"));
        mode6.getTargets().add(new TargetCreaturePermanent(filter));
        mode6.getTargets().add(new TargetCreaturePermanent(filter2));
        mode6.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode6);
    }

    public ClanDefiance(final ClanDefiance card) {
        super(card);
    }

    @Override
    public ClanDefiance copy() {
        return new ClanDefiance(this);
    }
    
    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<String>();
        rules.add("Choose one or more - Clan Defiance deals X damage to target creature with flying; Clan Defiance deals X damage to target creature without flying; and/or Clan Defiance deals X damage to target player.");
        return rules;
    }
}
