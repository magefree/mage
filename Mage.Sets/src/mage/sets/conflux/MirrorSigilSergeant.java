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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.game.permanent.token.EmptyToken;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class MirrorSigilSergeant extends CardImpl<MirrorSigilSergeant> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    private static final String rule = "At the beginning of your upkeep, if you control a blue permanent, you may put a token that's a copy of Mirror-Sigil Sergeant onto the battlefield.";

    public MirrorSigilSergeant(UUID ownerId) {
        super(ownerId, 12, "Mirror-Sigil Sergeant", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "CON";
        this.subtype.add("Rhino");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, if you control a blue permanent, you may put a token that's a copy of Mirror-Sigil Sergeant onto the battlefield.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MirrorSigilSergeantEffect(), TargetController.YOU, true);
        this.addAbility(new ConditionalTriggeredAbility(ability, new ControlsPermanentCondition(filter), rule));

    }

    public MirrorSigilSergeant(final MirrorSigilSergeant card) {
        super(card);
    }

    @Override
    public MirrorSigilSergeant copy() {
        return new MirrorSigilSergeant(this);
    }
}

class MirrorSigilSergeantEffect extends OneShotEffect<MirrorSigilSergeantEffect> {

    public MirrorSigilSergeantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token that's a copy of {this} onto the battlefield";
    }

    public MirrorSigilSergeantEffect(final MirrorSigilSergeantEffect effect) {
        super(effect);
    }

    @Override
    public MirrorSigilSergeantEffect copy() {
        return new MirrorSigilSergeantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        UUID targetId = source.getSourceId();
        if (targetId != null && player != null) {
            MageObject target = game.getPermanent(targetId);
            if (target == null) {
                target = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            }
            if (target != null) {
                if (target instanceof Permanent) {
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from((Permanent) target);
                    token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                    return true;
                }
            }
        }
        return false;
    }
}
