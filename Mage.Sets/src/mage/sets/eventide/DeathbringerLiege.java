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

package mage.sets.eventide;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class DeathbringerLiege extends CardImpl<DeathbringerLiege> {
    private final static FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures");
    private final static FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("black creatures");
    private final static FilterSpell filterWhiteSpellCard = new FilterSpell("a white spell");
    private final static FilterSpell filterBlackSpellCard = new FilterSpell("a black spell");

    static {
        filterWhite.setUseColor(true);
        filterWhite.getColor().setWhite(true);
        filterBlack.setUseColor(true);
        filterBlack.getColor().setBlack(true);
        filterWhiteSpellCard.setUseColor(true);
        filterWhiteSpellCard.getColor().setWhite(true);
        filterBlackSpellCard.setUseColor(true);
        filterBlackSpellCard.getColor().setBlack(true);
    }

    public DeathbringerLiege (UUID ownerId) {
        super(ownerId, 85, "Deathbringer Liege", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W/B}{W/B}{W/B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Horror");
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filterWhite, true)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filterBlack, true)));
        Ability ability = new SpellCastTriggeredAbility(new TapTargetEffect(), filterWhiteSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        ability = new SpellCastTriggeredAbility(new DeathbringerLiegeEffect(), filterBlackSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DeathbringerLiege (final DeathbringerLiege card) {
        super(card);
    }

    @Override
    public DeathbringerLiege copy() {
        return new DeathbringerLiege(this);
    }

}

class DeathbringerLiegeEffect extends OneShotEffect<DeathbringerLiegeEffect> {
    DeathbringerLiegeEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "destroy target creature if it's tapped";
    }

    DeathbringerLiegeEffect(final DeathbringerLiegeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if (p != null && p.isTapped()) {
            p.destroy(source.getSourceId(), game, false);
        }
        return false;
    }

    @Override
    public DeathbringerLiegeEffect copy() {
        return new DeathbringerLiegeEffect(this);
    }

}
