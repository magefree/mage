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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.a.AtzalCaveOfEternity;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class JourneyToEternity extends CardImpl {

    public JourneyToEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURA);

        this.transformable = true;
        this.secondSideCardClazz = AtzalCaveOfEternity.class;

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, return it to the battlefield under your control, then return Journey to Eternity to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        ability = new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderYourControlAttachedEffect("it"), "enchanted creature");
        ability.addEffect(new JourneyToEternityReturnTransformedSourceEffect());
        this.addAbility(ability);

    }

    public JourneyToEternity(final JourneyToEternity card) {
        super(card);
    }

    @Override
    public JourneyToEternity copy() {
        return new JourneyToEternity(this);
    }
}

class JourneyToEternityReturnTransformedSourceEffect extends OneShotEffect {

    public JourneyToEternityReturnTransformedSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then return {this} to the battlefield transformed under your control.";
    }

    public JourneyToEternityReturnTransformedSourceEffect(final JourneyToEternityReturnTransformedSourceEffect effect) {
        super(effect);
    }

    @Override
    public JourneyToEternityReturnTransformedSourceEffect copy() {
        return new JourneyToEternityReturnTransformedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            Zone zone = game.getState().getZone(card.getId());
            // cards needs to be in public non battlefield zone
            if (zone.equals(Zone.BATTLEFIELD) || !zone.isPublicZone()) {
                return true;
            }
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
        }
        return true;
    }
}
