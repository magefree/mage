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

import java.util.ArrayList;
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
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class BludgeonBrawl extends CardImpl<BludgeonBrawl> {

    public BludgeonBrawl(UUID ownerId) {
        super(ownerId, 80, "Bludgeon Brawl", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "NPH";

        this.color.setRed(true);

        // Each noncreature, non-Equipment artifact is an Equipment with equip {X} and "Equipped creature gets +X/+0," where X is that artifact's converted mana cost.
        this.addAbility(new BludgeonBrawlAbility());
    }

    public BludgeonBrawl(final BludgeonBrawl card) {
        super(card);
    }

    @Override
    public BludgeonBrawl copy() {
        return new BludgeonBrawl(this);
    }
}

class BludgeonBrawlAbility extends StaticAbility<BludgeonBrawlAbility> {

    public BludgeonBrawlAbility() {
        super(Zone.BATTLEFIELD, new BludgeonBrawlAddSubtypeEffect());
        this.addEffect(new BludgeonBrawlGainAbilityEffect());
    }

    public BludgeonBrawlAbility(BludgeonBrawlAbility ability) {
        super(ability);
    }

    @Override
    public BludgeonBrawlAbility copy() {
        return new BludgeonBrawlAbility(this);
    }

    @Override
    public String getRule() {
        return "Each noncreature, non-Equipment artifact is an Equipment with equip {X} and \"Equipped creature gets +X/+0,\" where X is that artifact's converted mana cost.";
    }
}

class BludgeonBrawlAddSubtypeEffect extends ContinuousEffectImpl<BludgeonBrawlAddSubtypeEffect> {

    public BludgeonBrawlAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    public BludgeonBrawlAddSubtypeEffect(final BludgeonBrawlAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactPermanent filter = new FilterArtifactPermanent("noncreature, non-Equipment artifact");
        filter.getNotCardType().add(CardType.CREATURE);
        filter.add(Predicates.not(new SubtypePredicate("Equipment")));

        List<UUID> affectedPermanents = new ArrayList<UUID>();
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.getSubtype().add("Equipment");
                affectedPermanents.add(permanent.getId());
            }
        }
        game.getState().setValue(source.getSourceId() + "BludgeonBrawlAffectedPermanents", affectedPermanents);
        return true;
    }

    @Override
    public BludgeonBrawlAddSubtypeEffect copy() {
        return new BludgeonBrawlAddSubtypeEffect(this);
    }
}

class BludgeonBrawlGainAbilityEffect extends ContinuousEffectImpl<BludgeonBrawlGainAbilityEffect> {

    public BludgeonBrawlGainAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public BludgeonBrawlGainAbilityEffect(final BludgeonBrawlGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public BludgeonBrawlGainAbilityEffect copy() {
        return new BludgeonBrawlGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> permanents = (List<UUID>) game.getState().getValue(source.getSourceId() + "BludgeonBrawlAffectedPermanents");
        if (permanents != null) {
            for (UUID permanentId : permanents) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    int convertedManaCost = permanent.getManaCost().convertedManaCost();
                    permanent.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(convertedManaCost)), game);
                    permanent.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(convertedManaCost, 0)), game);
                }
            }
            return true;
        }

        return false;
    }
}
