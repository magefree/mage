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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackYouOrPlaneswalkerAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class RiteOfTheRagingStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures named Lightning Rager");

    static {
        filter.add(new NamePredicate("Lightning Rager"));
    }

    public RiteOfTheRagingStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Creatures named Lightning Rager can't attack you or planeswalkers you control.
        Effect effect = new CantAttackYouOrPlaneswalkerAllEffect(Duration.WhileOnBattlefield, filter);
        effect.setText("Creatures named Lightning Rager can't attack you or planeswalkers you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each player's upkeep, that player puts a 5/1 red Elemental creature token named Lightning Rager onto the battlefield.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RiteOfTheRagingStormEffect(), TargetController.ANY, false));
    }

    public RiteOfTheRagingStorm(final RiteOfTheRagingStorm card) {
        super(card);
    }

    @Override
    public RiteOfTheRagingStorm copy() {
        return new RiteOfTheRagingStorm(this);
    }
}

class RiteOfTheRagingStormEffect extends OneShotEffect {

    private static final String effectText = "that player puts a 5/1 red Elemental creature token named Lightning Rager onto the battlefield. "
            + "It has trample, haste, and \"At the beginning of the end step, sacrifice this creature.\"";

    RiteOfTheRagingStormEffect() {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    RiteOfTheRagingStormEffect(RiteOfTheRagingStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Token lightningRagerToken = new LightningRagerToken();
            lightningRagerToken.putOntoBattlefield(1, game, this.getId(), player.getId());
        }
        return false;
    }

    @Override
    public RiteOfTheRagingStormEffect copy() {
        return new RiteOfTheRagingStormEffect(this);
    }
}

class LightningRagerToken extends Token {

    LightningRagerToken() {
        super("Lightning Rager", "5/1 red Elemental creature token named Lightning Rager onto the battlefield."
                + "It has trample, haste, and \"At the beginning of the end step, sacrifice this creature.\"");
        this.setOriginalExpansionSetCode("C15");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Elemental");
        power = new MageInt(5);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.NEXT, false));
    }
}
