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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class EssenceOfTheWild extends CardImpl<EssenceOfTheWild> {

    public EssenceOfTheWild(UUID ownerId) {
        super(ownerId, 178, "Essence of the Wild", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Avatar");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EssenceOfTheWildEffect()));
    }

    public EssenceOfTheWild(final EssenceOfTheWild card) {
        super(card);
    }

    @Override
    public EssenceOfTheWild copy() {
        return new EssenceOfTheWild(this);
    }
}

class EssenceOfTheWildEffect extends ReplacementEffectImpl<EssenceOfTheWildEffect> {

    public EssenceOfTheWildEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "Creatures you control enter the battlefield as a copy of {this}";
    }

    public EssenceOfTheWildEffect(EssenceOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm != null && perm.getCardType().contains(CardType.CREATURE) && perm.getControllerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            perm = perm.copy();
            perm.reset(game);
            perm.assignNewId();
            game.addEffect(new EssenceOfTheWildCopyEffect(perm, event.getTargetId()), source);
        }
        return false;
    }

    @Override
    public EssenceOfTheWildEffect copy() {
        return new EssenceOfTheWildEffect(this);
    }

}

class EssenceOfTheWildCopyEffect extends ContinuousEffectImpl<EssenceOfTheWildCopyEffect> {

    private Permanent essence;
    private UUID targetId;

    public EssenceOfTheWildCopyEffect(Permanent essence, UUID targetId) {
        super(Duration.EndOfGame, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.essence = essence;
        this.targetId = targetId;
    }

    public EssenceOfTheWildCopyEffect(final EssenceOfTheWildCopyEffect effect) {
        super(effect);
        this.essence = effect.essence.copy();
        this.targetId = effect.targetId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.setName(essence.getName());
            permanent.getColor().setColor(essence.getColor());
            permanent.getManaCost().clear();
            permanent.getManaCost().add(essence.getManaCost());
            permanent.getCardType().clear();
            for (CardType type: essence.getCardType()) {
                permanent.getCardType().add(type);
            }
            permanent.getSubtype().clear();
            for (String type: essence.getSubtype()) {
                permanent.getSubtype().add(type);
            }
            permanent.getSupertype().clear();
            for (String type: essence.getSupertype()) {
                permanent.getSupertype().add(type);
            }
            permanent.getAbilities().clear();
            for (Ability ability: essence.getAbilities()) {
                 permanent.addAbility(ability, game);
            }
            permanent.getPower().setValue(essence.getPower().getValue());
            permanent.getToughness().setValue(essence.getToughness().getValue());

            return true;
        }
        return false;
    }

    @Override
    public EssenceOfTheWildCopyEffect copy() {
        return new EssenceOfTheWildCopyEffect(this);
    }

}
