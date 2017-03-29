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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class ArmoryAutomaton extends CardImpl {

    public ArmoryAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add("Construct");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Armory Automaton enters the battlefield or attacks, attach any number of target Equipment to it.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ArmoryAutomatonEffect()));
    }

    public ArmoryAutomaton(final ArmoryAutomaton card) {
        super(card);
    }

    @Override
    public ArmoryAutomaton copy() {
        return new ArmoryAutomaton(this);
    }
}

class ArmoryAutomatonEffect extends OneShotEffect {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("Equipment");

    static {
        filter.add(new SubtypePredicate("Equipment"));
    }

    public ArmoryAutomatonEffect() {
        super(Outcome.Benefit);
        this.staticText = "attach any number of target Equipment to it";
    }

    public ArmoryAutomatonEffect(final ArmoryAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public ArmoryAutomatonEffect copy() {
        return new ArmoryAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game).size() - sourcePermanent.getAttachments().size();
            while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.Benefit, "Attach a target Equipment?", source, game)) {
                Target targetEquipment = new TargetPermanent(filter);
                if (player.choose(Outcome.Benefit, targetEquipment, source.getSourceId(), game)) {
                    Permanent aura = game.getPermanent(targetEquipment.getFirstTarget());
                    if (aura != null) {
                        Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
                        if (attachedTo != null) {
                            attachedTo.removeAttachment(aura.getId(), game);
                        }
                        sourcePermanent.addAttachment(aura.getId(), game);
                    }
                }
                countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game).size() - sourcePermanent.getAttachments().size();
            }
            return true;
        }
        return false;
    }
}
