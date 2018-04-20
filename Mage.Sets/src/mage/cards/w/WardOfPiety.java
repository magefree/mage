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
package mage.cards.w;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WardOfPiety extends CardImpl {

    public WardOfPiety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PreventDamage));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {1}{W}: The next 1 damage that would be dealt to enchanted creature this turn is dealt to target creature or player instead.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WardOfPietyPreventDamageTargetEffect(), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public WardOfPiety(final WardOfPiety card) {
        super(card);
    }

    @Override
    public WardOfPiety copy() {
        return new WardOfPiety(this);
    }
}

class WardOfPietyPreventDamageTargetEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public WardOfPietyPreventDamageTargetEffect() {
        super(Duration.EndOfTurn, 1, true);
        staticText = "The next 1 damage that would be dealt to enchanted creature this turn is dealt to target creature or player instead";
    }

    public WardOfPietyPreventDamageTargetEffect(final WardOfPietyPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public WardOfPietyPreventDamageTargetEffect copy() {
        return new WardOfPietyPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
            if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game))) {
                redirectTarget = source.getTargets().get(0);
                return true;
            }
        }
        return false;
    }

}
