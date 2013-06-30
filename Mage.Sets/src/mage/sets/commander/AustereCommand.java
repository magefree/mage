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
package mage.sets.commander;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

/**
 *
 * @author LevelX2
 */
public class AustereCommand extends CardImpl<AustereCommand> {

    private static final FilterCreaturePermanent filter3orLess = new FilterCreaturePermanent("creatures with converted mana cost 3 or less");
    private static final FilterCreaturePermanent filter4orMore = new FilterCreaturePermanent("creatures with converted mana cost 4 or greater");
    static {
        filter3orLess.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 4));
        filter4orMore.add(new ConvertedManaCostPredicate(Filter.ComparisonType.GreaterThan, 3));
    }

    public AustereCommand(UUID ownerId) {
        super(ownerId, 8, "Austere Command", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "CMD";

        this.color.setWhite(true);

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Destroy all artifacts;
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterArtifactPermanent("artifacts")));
        // or destroy all enchantments;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy all creatures with converted mana cost 3 or less;
        mode = new Mode();
        mode.getEffects().add(new DestroyAllEffect(filter3orLess));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy all creatures with converted mana cost 4 or greater.
        mode = new Mode();
        mode.getEffects().add(new DestroyAllEffect(filter4orMore));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public AustereCommand(final AustereCommand card) {
        super(card);
    }

    @Override
    public AustereCommand copy() {
        return new AustereCommand(this);
    }
}
