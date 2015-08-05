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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class ObeliskOfUrd extends CardImpl {

    public ObeliskOfUrd(UUID ownerId) {
        super(ownerId, 222, "Obelisk of Urd", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "M15";

        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // As Obelisk of Urd enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ObeliskOfUrdEnterBattlefieldEffect()));
        
        // Creatures you control of the chosen type get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ObeliskOfUrdBoostEffect()));
    }

    public ObeliskOfUrd(final ObeliskOfUrd card) {
        super(card);
    }

    @Override
    public ObeliskOfUrd copy() {
        return new ObeliskOfUrd(this);
    }
}

class ObeliskOfUrdEnterBattlefieldEffect extends OneShotEffect {
    
    ObeliskOfUrdEnterBattlefieldEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose a creature type";
    }
    
    ObeliskOfUrdEnterBattlefieldEffect(final ObeliskOfUrdEnterBattlefieldEffect effect) {
        super(effect);
    }
    
    @Override
    public ObeliskOfUrdEnterBattlefieldEffect copy() {
        return new ObeliskOfUrdEnterBattlefieldEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            game.getState().setValue(permanent.getId() + "_type", typeChoice.getChoice());
            permanent.addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
        }
        return false;
    }
}

class ObeliskOfUrdBoostEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    ObeliskOfUrdBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen type get +2/+2.";
    }

    ObeliskOfUrdBoostEffect(final ObeliskOfUrdBoostEffect effect) {
        super(effect);
    }

    @Override
    public ObeliskOfUrdBoostEffect copy() {
        return new ObeliskOfUrdBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
            if (subtype != null) {
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (perm.hasSubtype(subtype)) {
                        perm.addPower(2);
                        perm.addToughness(2);
                    }
                }
            }
        }
        return true;
    }
}
