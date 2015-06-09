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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class SarkhanTheDragonspeaker extends CardImpl {

    public SarkhanTheDragonspeaker(UUID ownerId) {
        super(ownerId, 119, "Sarkhan, the Dragonspeaker", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Sarkhan");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Until end of turn, Sarkhan, the Dragonspeaker becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.
        this.addAbility(new LoyaltyAbility(new SarkhanTheDragonspeakerEffect(), 1));
        
        // -3: Sarkhan, the Dragonspeaker deals 4 damage to target creature.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // -6: You get an emblem with "At the beginning of your draw step, draw two additional cards" and "At the beginning of your end step, discard your hand."
        Effect effect = new GetEmblemEffect(new SarkhanTheDragonspeakerEmblem());
        effect.setText("You get an emblem with \"At the beginning of your draw step, draw two additional cards\" and \"At the beginning of your end step, discard your hand.\"");
        this.addAbility(new LoyaltyAbility(effect, -6));
    }

    public SarkhanTheDragonspeaker(final SarkhanTheDragonspeaker card) {
        super(card);
    }

    @Override
    public SarkhanTheDragonspeaker copy() {
        return new SarkhanTheDragonspeaker(this);
    }
}

class SarkhanTheDragonspeakerEffect extends ContinuousEffectImpl {
    
    SarkhanTheDragonspeakerEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Until end of turn, {this} becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.";
    }

    SarkhanTheDragonspeakerEffect(final SarkhanTheDragonspeakerEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanTheDragonspeakerEffect copy() {
        return new SarkhanTheDragonspeakerEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getCardType().clear();
                        permanent.getCardType().add(CardType.CREATURE);
                        permanent.getSubtype().clear();
                        permanent.getSubtype().add("Dragon");
                        permanent.getSupertype().clear();
                        permanent.getSupertype().add("Legendary");
                    }
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.RED);
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(4);
                        permanent.getToughness().setValue(4);
                    }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}

class SarkhanTheDragonspeakerEmblem extends Emblem {

    SarkhanTheDragonspeakerEmblem() {
        setName("EMBLEM: Sarkhan, the Dragonspeaker");
        this.setExpansionSetCodeForImage("KTK");

        this.getAbilities().add(new BeginningOfDrawTriggeredAbility(Zone.COMMAND, new DrawCardSourceControllerEffect(2), TargetController.YOU, false));
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new DiscardHandControllerEffect(), TargetController.YOU, null, false));
    }
}
