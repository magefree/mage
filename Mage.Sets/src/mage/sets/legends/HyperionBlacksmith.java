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
package mage.sets.legends;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public class HyperionBlacksmith extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public HyperionBlacksmith(UUID ownerId) {
        super(ownerId, 150, "Hyperion Blacksmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "LEG";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: You may tap or untap target artifact an opponent controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MayTapOrUntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        this.addAbility(ability);
    }

    public HyperionBlacksmith(final HyperionBlacksmith card) {
        super(card);
    }

    @Override
    public HyperionBlacksmith copy() {
        return new HyperionBlacksmith(this);
    }
}
