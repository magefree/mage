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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.CardUtil;
import mage.util.GameLog;

/**
 *
 * @author jeffwadsworth
 */
public class DragonlordSilumgar extends CardImpl {

    public DragonlordSilumgar(UUID ownerId) {
        super(ownerId, 220, "Dragonlord Silumgar", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.expansionSetCode = "DTK";
        this.supertype.add("Legendary");
        this.subtype.add("Elder");
        this.subtype.add("Dragon");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Dragonlord Silumgar enters the battlefield, gain control of target creature or planeswalker for as long as you control Dragonlord Silumgar.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DragonlordSilumgarEffect(), false);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

    }

    public DragonlordSilumgar(final DragonlordSilumgar card) {
        super(card);
    }

    @Override
    public DragonlordSilumgar copy() {
        return new DragonlordSilumgar(this);
    }
}

class DragonlordSilumgarEffect extends OneShotEffect {

    public DragonlordSilumgarEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of target creature or planeswalker for as long as you control {this}";
    }

    public DragonlordSilumgarEffect(final DragonlordSilumgarEffect effect) {
        super(effect);
    }

    @Override
    public DragonlordSilumgarEffect copy() {
        return new DragonlordSilumgarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && sourcePermanent != null && target != null
                && controller.getId().equals(sourcePermanent.getControllerId())) {
            game.addEffect(new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), new SourceOnBattlefieldControlUnchangedCondition(), null), source);
            if (!game.isSimulation()) {
                game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " gained control of " + target.getLogName());
            }
            sourcePermanent.addInfo("gained control of", CardUtil.addToolTipMarkTags("Gained control of: " + GameLog.getColoredObjectIdNameForTooltip(target)), game);
        }
        return false;
    }
}
