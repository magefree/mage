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

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public class DauntlessBodyguard extends CardImpl {

    public DauntlessBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Dauntless Bodyguard enters the battlefield, choose another creature you control. 
        this.addAbility(new AsEntersBattlefieldAbility(new DauntlessBodyguardChooseCreatureEffect()));

        // Sacrifice Dauntless Bodyguard: The chosen creature gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DauntlessBodyguardGainAbilityEffect(), new SacrificeSourceCost()));

    }

    public DauntlessBodyguard(final DauntlessBodyguard card) {
        super(card);
    }

    @Override
    public DauntlessBodyguard copy() {
        return new DauntlessBodyguard(this);
    }
}

class DauntlessBodyguardChooseCreatureEffect extends OneShotEffect {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");
    
    static {
        filter.add(new AnotherPredicate());
    }

    public DauntlessBodyguardChooseCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose another creature you control";
    }

    public DauntlessBodyguardChooseCreatureEffect(final DauntlessBodyguardChooseCreatureEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessBodyguardChooseCreatureEffect copy() {
        return new DauntlessBodyguardChooseCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (controller != null && mageObject != null) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, filter, true);
            if (controller.choose(this.outcome, target, source.getSourceId(), game)) {
                Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
                if (chosenCreature != null) {
                    game.getState().setValue(mageObject.getId() + "_chosenCreature", new MageObjectReference(chosenCreature, game));
                    if (mageObject instanceof Permanent) {
                        ((Permanent) mageObject).addInfo("chosen creature", CardUtil.addToolTipMarkTags("Chosen creature: " + chosenCreature.getIdName()), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DauntlessBodyguardGainAbilityEffect extends GainAbilityTargetEffect {
    
    private MageObjectReference mor;

    public DauntlessBodyguardGainAbilityEffect() {
        super(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        this.staticText = "The chosen creature gains indestructible until end of turn";
    }

    public DauntlessBodyguardGainAbilityEffect(final DauntlessBodyguardGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessBodyguardGainAbilityEffect copy() {
        return new DauntlessBodyguardGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (mor == null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                mor = (MageObjectReference) game.getState().getValue(sourcePermanent.getId() + "_chosenCreature");
            }
        }
        if (mor != null) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                permanent.addAbility(ability, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
