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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class StoneIdolTrap extends CardImpl<StoneIdolTrap> {


    public StoneIdolTrap(UUID ownerId) {
        super(ownerId, 93, "Stone Idol Trap", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setRed(true);

        // Stone Idol Trap costs {1} less to cast for each attacking creature.
        Ability ability = new SimpleStaticAbility(Constants.Zone.STACK, new StoneIdolTrapCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Put a 6/12 colorless Construct artifact creature token with trample onto the battlefield. Exile it at the beginning of your next end step.
        this.getSpellAbility().addEffect(new StoneIdolTrapEffect());
    }

    public StoneIdolTrap(final StoneIdolTrap card) {
        super(card);
    }

    @Override
    public StoneIdolTrap copy() {
        return new StoneIdolTrap(this);
    }
}

class StoneIdolTrapCostReductionEffect extends CostModificationEffectImpl<StoneIdolTrapCostReductionEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AttackingPredicate());
    }
    public StoneIdolTrapCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        staticText = "{this} costs {1} less to cast for each attacking creature";
    }

    protected StoneIdolTrapCostReductionEffect(StoneIdolTrapCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield().getAllActivePermanents(filter, game).size();
        CardUtil.adjustCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public StoneIdolTrapCostReductionEffect copy() {
        return new StoneIdolTrapCostReductionEffect(this);
    }
}

class StoneIdolTrapEffect extends OneShotEffect<StoneIdolTrapEffect> {

    public StoneIdolTrapEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "Put a 6/12 colorless Construct artifact creature token with trample onto the battlefield. Exile it at the beginning of your next end step";
    }

    public StoneIdolTrapEffect(final StoneIdolTrapEffect effect) {
        super(effect);
    }

    @Override
    public StoneIdolTrapEffect copy() {
        return new StoneIdolTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StoneTrapIdolToken token = new StoneTrapIdolToken();
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        ExileTargetEffect exileEffect = new ExileTargetEffect("exile the token");
        exileEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
        DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect, Constants.TargetController.YOU);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        
        return true;
    }
}

class StoneTrapIdolToken extends Token {

    public StoneTrapIdolToken() {
        super("Construct", "6/12 construct artifact creature token with trample");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add("Construct");
        power = new MageInt(6);
        toughness = new MageInt(12);
        addAbility(TrampleAbility.getInstance());
    }
}
