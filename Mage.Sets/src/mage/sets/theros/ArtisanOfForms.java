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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class ArtisanOfForms extends CardImpl {

    public ArtisanOfForms(UUID ownerId) {
        super(ownerId, 40, "Artisan of Forms", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "THS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> - Whenever you cast a spell that targets Artisan of Forms, you may have Artisan of Forms become a copy of target creature and gain this ability.
        Effect effect = new CopyPermanentEffect(new FilterCreaturePermanent(), new ArtisanOfFormsApplyToPermanent(), true);
        effect.setText("have {this} become a copy of target creature and gain this ability");
        Ability ability = new HeroicAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ArtisanOfForms(final ArtisanOfForms card) {
        super(card);
    }

    @Override
    public ArtisanOfForms copy() {
        return new ArtisanOfForms(this);
    }
}

class ArtisanOfFormsApplyToPermanent extends ApplyToPermanent {

    @Override
    public Boolean apply(Game game, MageObject mageObject) {
        Effect effect = new CopyPermanentEffect(new ArtisanOfFormsApplyToPermanent());
        effect.setText("have {this} become a copy of target creature and gain this ability");
        mageObject.getAbilities().add(new HeroicAbility(effect, true));
        return true;
    }

    @Override
    public Boolean apply(Game game, Permanent permanent) {
        Effect effect = new CopyPermanentEffect(new ArtisanOfFormsApplyToPermanent());
        effect.setText("have {this} become a copy of target creature and gain this ability");
        permanent.addAbility(new HeroicAbility(effect, true), game);
        return true;
    }

}
