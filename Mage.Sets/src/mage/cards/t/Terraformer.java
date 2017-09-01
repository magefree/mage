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
package mage.cards.t;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class Terraformer extends CardImpl {

    public Terraformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}: Choose a basic land type. Each land you control becomes that type until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TerraformerEffect(), new GenericManaCost(1)));
    }

    public Terraformer(final Terraformer card) {
        super(card);
    }

    @Override
    public Terraformer copy() {
        return new Terraformer(this);
    }
}

class TerraformerEffect extends OneShotEffect {

    TerraformerEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose a basic land type. Each land you control becomes that type until end of turn";
    }

    TerraformerEffect(final TerraformerEffect effect) {
        super(effect);
    }

    @Override
    public TerraformerEffect copy() {
        return new TerraformerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice choice = new ChoiceBasicLandType();
            if (player.choose(Outcome.Neutral, choice, game)) {
                game.getState().setValue(source.getSourceId().toString() + "_Terraformer", choice.getChoice());
            }
            game.addEffect(new TerraformerContinuousEffect(), source);
            return true;
        }
        return false;
    }
}

class TerraformerContinuousEffect extends ContinuousEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    TerraformerContinuousEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
    }

    TerraformerContinuousEffect(final TerraformerContinuousEffect effect) {
        super(effect);
    }

    @Override
    public TerraformerContinuousEffect copy() {
        return new TerraformerContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        String choice = (String) game.getState().getValue(source.getSourceId().toString() + "_Terraformer");
        if (choice != null) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                Permanent land = it.next().getPermanent(game);
                if (land != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                land.getSubtype(game).clear();
                                land.getSubtype(game).add(choice);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                land.getAbilities().clear();
                                if (choice.equals("Forest")) {
                                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                }
                                if (choice.equals("Plains")) {
                                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                }
                                if (choice.equals("Mountain")) {
                                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                }
                                if (choice.equals("Island")) {
                                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                }
                                if (choice.equals("Swamp")) {
                                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                }
                            }
                            break;
                    }
                } else {
                    it.remove();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
