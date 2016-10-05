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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.ColorChangingEffects_5;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class ImprisonedInTheMoon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, land, or planeswalker");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public ImprisonedInTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add("Aura");

        // Enchant creature, land, or planeswalker
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent is a colorless land with "{T}: Add {C} to your mana pool" and loses all other card types and abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesColorlessLandEffect()));
    }

    public ImprisonedInTheMoon(final ImprisonedInTheMoon card) {
        super(card);
    }

    @Override
    public ImprisonedInTheMoon copy() {
        return new ImprisonedInTheMoon(this);
    }
}

class BecomesColorlessLandEffect extends ContinuousEffectImpl {

    public BecomesColorlessLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Enchanted permanent is a colorless land with \"{T}: Add {C} to your mana pool\" and loses all other card types and abilities";
    }

    public BecomesColorlessLandEffect(final BecomesColorlessLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesColorlessLandEffect copy() {
        return new BecomesColorlessLandEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setWhite(false);
                        permanent.getColor(game).setGreen(false);
                        permanent.getColor(game).setBlack(false);
                        permanent.getColor(game).setBlue(false);
                        permanent.getColor(game).setRed(false);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                        break;
                    case TypeChangingEffects_4:
                        boolean isLand = permanent.getCardType().contains(CardType.LAND);
                        permanent.getCardType().clear();
                        permanent.getCardType().add(CardType.LAND);
                        if (!isLand) {
                            permanent.getSubtype(game).clear();
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
