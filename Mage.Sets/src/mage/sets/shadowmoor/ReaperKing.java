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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class ReaperKing extends CardImpl<ReaperKing> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Scarecrow creatures");
    private static final FilterCreaturePermanent filterTrigger = new FilterCreaturePermanent("another Scarecrow");

    static {
        filter.add(new SubtypePredicate("Scarecrow"));
        filterTrigger.add(new AnotherPredicate());
        filterTrigger.add(new SubtypePredicate("Scarecrow"));
    }

    public ReaperKing(UUID ownerId) {
        super(ownerId, 260, "Reaper King", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2/W}{2/U}{2/B}{2/R}{2/G}");
        this.expansionSetCode = "SHM";
        this.supertype.add("Legendary");
        this.subtype.add("Scarecrow");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Other Scarecrow creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        // Whenever another Scarecrow enters the battlefield under your control, destroy target permanent.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new DestroyTargetEffect(), filterTrigger);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    public ReaperKing(final ReaperKing card) {
        super(card);
    }

    @Override
    public ReaperKing copy() {
        return new ReaperKing(this);
    }
}
