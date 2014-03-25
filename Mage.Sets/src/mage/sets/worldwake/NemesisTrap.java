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
package mage.sets.worldwake;

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class NemesisTrap extends CardImpl<NemesisTrap> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("If a white creature is attacking");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new AttackingPredicate());
    }
  
    
    public NemesisTrap(UUID ownerId) {
        super(ownerId, 61, "Nemesis Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setBlack(true);

        // If a white creature is attacking, you may pay {B}{B} rather than pay Nemesis Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{B}{B}"), new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 0, false)));

        // Exile target attacking creature. Put a token that's a copy of that creature onto the battlefield. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new NemesisTrapEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    public NemesisTrap(final NemesisTrap card) {
        super(card);
    }

    @Override
    public NemesisTrap copy() {
        return new NemesisTrap(this);
    }
}

class NemesisTrapEffect extends OneShotEffect<NemesisTrapEffect> {

    public NemesisTrapEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target attacking creature. Put a token that's a copy of that creature onto the battlefield. Exile it at the beginning of the next end step";
    }

    public NemesisTrapEffect(final NemesisTrapEffect effect) {
        super(effect);
    }

    @Override
    public NemesisTrapEffect copy() {
        return new NemesisTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetedCreature != null) {
            controller.moveCardToExileWithInfo(targetedCreature, null, null, source.getSourceId(), game, Zone.BATTLEFIELD);
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(targetedCreature);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            token.addAbility(new AtEndOfTurnDelayedTriggeredAbility(new ExileSourceEffect()));
            return true;
        }
        return false;
    }
}
