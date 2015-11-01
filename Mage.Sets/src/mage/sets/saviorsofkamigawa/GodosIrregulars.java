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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GodosIrregulars extends CardImpl {

    private static final FilterCreaturePermanent basicFilter = new FilterCreaturePermanent("creature blocking it");

    static {
        basicFilter.add(new BlockingPredicate());
    }

    public UUID originalAbilityIdToAdjust;

    public GodosIrregulars(UUID ownerId) {
        super(ownerId, 101, "Godo's Irregulars", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Godo's Irregulars deals 1 damage to target creature blocking it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{R"));
        ability.addTarget(new TargetCreaturePermanent());
        originalAbilityIdToAdjust = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (originalAbilityIdToAdjust.equals(ability.getOriginalId())) {
            ability.getTargets().clear();
            FilterCreaturePermanent filter = basicFilter.copy();
            filter.add(new BlockingAttackerIdPredicate(this.getId()));
            Target target = new TargetCreaturePermanent(filter);
            ability.addTarget(target);
        }
    }

    public GodosIrregulars(final GodosIrregulars card) {
        super(card);
        this.originalAbilityIdToAdjust = card.originalAbilityIdToAdjust;
    }

    @Override
    public GodosIrregulars copy() {
        return new GodosIrregulars(this);
    }
}
