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
package mage.sets.returntoravnica;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class IzzetStaticaster extends CardImpl<IzzetStaticaster> {

    public IzzetStaticaster(UUID ownerId) {
        super(ownerId, 173, "Izzet Staticaster", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Wizzard");
        this.color.setBlue(true);
        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash (You may cast this spell any time you could cast an instant.)
        this.addAbility(FlashAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new IzzetStaticasterDamageEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public IzzetStaticaster(final IzzetStaticaster card) {
        super(card);
    }

    @Override
    public IzzetStaticaster copy() {
        return new IzzetStaticaster(this);
    }
}

class IzzetStaticasterDamageEffect extends OneShotEffect<IzzetStaticasterDamageEffect> {

    public IzzetStaticasterDamageEffect() {
        super(Constants.Outcome.Exile);
        this.staticText = "{this} deals 1 damage to target creature and each other creature with the same name as that creature";
    }

    public IzzetStaticasterDamageEffect(final IzzetStaticasterDamageEffect effect) {
        super(effect);
    }

    @Override
    public IzzetStaticasterDamageEffect copy() {
        return new IzzetStaticasterDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetPermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new NamePredicate(targetPermanent.getName()));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getId(), game);
            for (Permanent permanent : permanents) {
                permanent.damage(1, source.getSourceId(), game, true, false);
            }
            return true;
        }
        return false;
    }
}