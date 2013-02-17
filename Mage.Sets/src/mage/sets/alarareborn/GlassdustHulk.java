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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UnblockableSourceEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Loki
 */
public class GlassdustHulk extends CardImpl<GlassdustHulk> {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public GlassdustHulk(UUID ownerId) {
        super(ownerId, 7, "Glassdust Hulk", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Golem");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another artifact enters the battlefield under your control, Glassdust Hulk gets +1/+1 until end of turn and is unblockable this turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter,
                "Whenever another artifact enters the battlefield under your control, Glassdust Hulk gets +1/+1 until end of turn and is unblockable this turn.");
        ability.addEffect(new UnblockableSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
        
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{W/U}")));
    }

    public GlassdustHulk(final GlassdustHulk card) {
        super(card);
    }

    @Override
    public GlassdustHulk copy() {
        return new GlassdustHulk(this);
    }
}
