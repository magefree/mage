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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public class GlissaSunseeker extends CardImpl {

    public GlissaSunseeker(UUID ownerId) {
        super(ownerId, 120, "Glissa Sunseeker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "MRD";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // {tap}: Destroy target artifact if its converted mana cost is equal to the amount of mana in your mana pool.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GlissaSunseekerEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public GlissaSunseeker(final GlissaSunseeker card) {
        super(card);
    }

    @Override
    public GlissaSunseeker copy() {
        return new GlissaSunseeker(this);
    }
}

class GlissaSunseekerEffect extends OneShotEffect {

    public GlissaSunseekerEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact if its converted mana cost is equal to the amount of mana in your mana pool";
    }

    public GlissaSunseekerEffect(final GlissaSunseekerEffect effect) {
        super(effect);
    }

    @Override
    public GlissaSunseekerEffect copy() {
        return new GlissaSunseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ManaPool pool = controller.getManaPool();
        int blackMana = pool.getBlack();
        int whiteMana = pool.getWhite();
        int blueMana = pool.getBlue();
        int greenMana = pool.getGreen();
        int redMana = pool.getRed();
        int colorlessMana = pool.getColorless();
        int manaPoolTotal = blackMana + whiteMana + blueMana + greenMana + redMana + colorlessMana;
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent.getManaCost().convertedManaCost() == manaPoolTotal) {
                return permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return false;
    }
}