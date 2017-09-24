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
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public class ReignOfChaos extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Plains");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("white creature");
    private static final FilterPermanent filter3 = new FilterPermanent("Island");
    private static final FilterCreaturePermanent filter4 = new FilterCreaturePermanent("blue creature");

    static {
        filter1.add(new SubtypePredicate(SubType.PLAINS));
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
        filter3.add(new SubtypePredicate(SubType.ISLAND));
        filter4.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ReignOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose one - Destroy target Plains and target white creature; or destroy target Island and target blue creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(false, true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect(false, true));
        mode.getTargets().add(new TargetPermanent(filter3));
        mode.getTargets().add(new TargetPermanent(filter4));
        this.getSpellAbility().addMode(mode);
    }

    public ReignOfChaos(final ReignOfChaos card) {
        super(card);
    }

    @Override
    public ReignOfChaos copy() {
        return new ReignOfChaos(this);
    }
}
