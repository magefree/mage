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
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class BludgeonBrawl extends CardImpl {

    public BludgeonBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

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

class BludgeonBrawlAbility extends StaticAbility {

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

class BludgeonBrawlAddSubtypeEffect extends ContinuousEffectImpl {

    public BludgeonBrawlAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    public BludgeonBrawlAddSubtypeEffect(final BludgeonBrawlAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactPermanent filter = new FilterArtifactPermanent("noncreature, non-Equipment artifact");
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(Predicates.not(new SubtypePredicate(SubType.EQUIPMENT)));

        Cards affectedPermanents = new CardsImpl();
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.getSubtype(game).add("Equipment");
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

class BludgeonBrawlGainAbilityEffect extends ContinuousEffectImpl {

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
        Cards permanents = (Cards) game.getState().getValue(source.getSourceId() + "BludgeonBrawlAffectedPermanents");
        if (permanents != null) {
            for (UUID permanentId : permanents) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    int convertedManaCost = permanent.getConvertedManaCost();
                    permanent.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(convertedManaCost)), game);
                    permanent.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(convertedManaCost, 0)), game);
                }
            }
            return true;
        }

        return false;
    }
}
