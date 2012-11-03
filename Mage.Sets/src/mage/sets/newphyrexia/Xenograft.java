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
package mage.sets.newphyrexia;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Xenograft extends CardImpl<Xenograft> {

    public Xenograft(UUID ownerId) {
        super(ownerId, 51, "Xenograft", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "NPH";

        this.color.setBlue(true);

        // As Xenograft enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new XenograftEffect()));
        // Each creature you control is the chosen type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new XenograftAddSubtypeEffect()));
    }

    public Xenograft(final Xenograft card) {
        super(card);
    }

    @Override
    public Xenograft copy() {
        return new Xenograft(this);
    }
}

class XenograftEffect extends OneShotEffect<XenograftEffect> {

    public XenograftEffect() {
        super(Outcome.DrawCard);
        staticText = "choose a creature type";
    }

    public XenograftEffect(final XenograftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                game.debugMessage("player canceled choosing type. retrying.");
            }
            game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + typeChoice.getChoice());
            game.getState().setValue(source.getSourceId() + "_XenograftType", typeChoice.getChoice());
        }
        return false;
    }

    @Override
    public XenograftEffect copy() {
        return new XenograftEffect(this);
    }
}

class XenograftAddSubtypeEffect extends ContinuousEffectImpl<XenograftAddSubtypeEffect> {

    public XenograftAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Each creature you control is the chosen type in addition to its other types";
    }

    public XenograftAddSubtypeEffect(final XenograftAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String subtype = (String) game.getState().getValue(source.getSourceId() + "_XenograftType");
        if (subtype != null) {
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
            for (Permanent permanent : permanents) {
                if (permanent != null && !permanent.getSubtype().contains(subtype)) {
                    permanent.getSubtype().add(subtype);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public XenograftAddSubtypeEffect copy() {
        return new XenograftAddSubtypeEffect(this);
    }
}
