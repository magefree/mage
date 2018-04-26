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
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.t.TombOfTheDuskRose;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ProfaneProcession extends CardImpl {

    public ProfaneProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        this.transformable = true;
        this.secondSideCardClazz = TombOfTheDuskRose.class;

        // {3}{W}{B}: Exile target creature. Then if there are three or more cards exiled with Profane Procession, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProfaneProcessionEffect(), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ProfaneProcession(final ProfaneProcession card) {
        super(card);
    }

    @Override
    public ProfaneProcession copy() {
        return new ProfaneProcession(this);
    }
}

class ProfaneProcessionEffect extends OneShotEffect {

    public ProfaneProcessionEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature. Then if there are three or more cards exiled with {this}, transform it.";
    }

    public ProfaneProcessionEffect(final ProfaneProcessionEffect effect) {
        super(effect);
    }

    @Override
    public ProfaneProcessionEffect copy() {
        return new ProfaneProcessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && exileId != null && sourceObject != null) {
            new ExileTargetEffect(exileId, sourceObject.getIdName()).setTargetPointer(targetPointer).apply(game, source);
            game.applyEffects();
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null && exileZone.size() > 2) {
                new TransformSourceEffect(true).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
