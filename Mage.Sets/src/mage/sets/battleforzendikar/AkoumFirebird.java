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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachTurnStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class AkoumFirebird extends CardImpl {

    public AkoumFirebird(UUID ownerId) {
        super(ownerId, 138, "Akoum Firebird", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Phoenix");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Akoum Firebird attacks each turn if able.
        this.addAbility(new AttacksEachTurnStaticAbility());

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, you may pay {4}{R}{R}.
        // If you do, return Akoum Firebird from your graveyard to the battlefield.
        this.addAbility(new AkoumFirebirdLandfallAbility(new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{4}{R}{R}")), false));
    }

    public AkoumFirebird(final AkoumFirebird card) {
        super(card);
    }

    @Override
    public AkoumFirebird copy() {
        return new AkoumFirebird(this);
    }
}

class AkoumFirebirdLandfallAbility extends TriggeredAbilityImpl {

    public AkoumFirebirdLandfallAbility(Effect effect, boolean optional) {
        this(Zone.GRAVEYARD, effect, optional);
    }

    public AkoumFirebirdLandfallAbility (Zone zone, Effect effect, Boolean optional ) {
        super(zone, effect, optional);
    }

    public AkoumFirebirdLandfallAbility(final AkoumFirebirdLandfallAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "<i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, " + super.getRule();
    }

    @Override
    public AkoumFirebirdLandfallAbility copy() {
        return new AkoumFirebirdLandfallAbility(this);
    }
}