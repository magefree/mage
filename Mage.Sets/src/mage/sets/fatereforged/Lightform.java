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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Lightform extends CardImpl {

    public Lightform(UUID ownerId) {
        super(ownerId, 16, "Lightform", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "FRF";

        // When Lightform enters the battlefield, it becomes an Aura with enchant creature. Manifest the top card of your library and attach Lightform to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesAuraManifestAttachToEffect()));

        // Enchanted creature has flying and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public Lightform(final Lightform card) {
        super(card);
    }

    @Override
    public Lightform copy() {
        return new Lightform(this);
    }
}

class BecomesAuraManifestAttachToEffect extends OneShotEffect {

    public BecomesAuraManifestAttachToEffect() {
        super(Outcome.Benefit);
        this.staticText = "it becomes an Aura with enchant creature. Manifest the top card of your library and attach {this} to it";
    }

    public BecomesAuraManifestAttachToEffect(final BecomesAuraManifestAttachToEffect effect) {
        super(effect);
    }

    @Override
    public BecomesAuraManifestAttachToEffect copy() {
        return new BecomesAuraManifestAttachToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (controller != null && enchantment != null) {
            // manifest top card
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                new ManifestEffect(1).apply(game, source);
                Permanent enchantedCreature = game.getPermanent(card.getId());
                if (enchantedCreature != null) {
                    enchantedCreature.addAttachment(enchantment.getId(), game);
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    Target target = new TargetCreaturePermanent(filter);
                    target.addTarget(enchantedCreature.getId(), source, game);
                    game.addEffect(new BecomesAuraEffect(target), source);
                }
            }
            return true;
        }
        return false;
    }
}

class BecomesAuraEffect extends ContinuousEffectImpl implements SourceEffect {

    private Ability newAbility;
    Target target;

    public BecomesAuraEffect(Target target) {
        super(Duration.Custom, Outcome.AddAbility);
        this.target = target;
        newAbility = new EnchantAbility(target.getTargetName());
        newAbility.setRuleAtTheTop(true);
        staticText = "it becomes an Aura with enchant " + target.getTargetName();
        
    }


    public BecomesAuraEffect(final BecomesAuraEffect effect) {
        super(effect);
        this.target = effect.target;
        this.newAbility = effect.newAbility;
    }

    @Override
    public BecomesAuraEffect copy() {
        return new BecomesAuraEffect(this);
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
                        if (!permanent.getSubtype().contains("Aura")) {
                            permanent.getSubtype().add("Aura");
                        }
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(newAbility, source.getSourceId(), game);
                        permanent.getSpellAbility().getTargets().clear();
                        permanent.getSpellAbility().getTargets().add(target);
                    }
            }
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.AbilityAddingRemovingEffects_6.equals(layer) || Layer.TypeChangingEffects_4.equals(layer);
    }

}
