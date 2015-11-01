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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Realmwright extends CardImpl {

    public Realmwright(UUID ownerId) {
        super(ownerId, 45, "Realmwright", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Vedalken");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As Realmwright enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.Neutral)));

        // Lands you control are the chosen type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RealmwrightEffect2()));
    }

    public Realmwright(final Realmwright card) {
        super(card);
    }

    @Override
    public Realmwright copy() {
        return new Realmwright(this);
    }
}

class RealmwrightEffect2 extends ContinuousEffectImpl {

    public RealmwrightEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Lands you control are the chosen type in addition to their other types";
    }

    public RealmwrightEffect2(final RealmwrightEffect2 effect) {
        super(effect);
    }

    @Override
    public RealmwrightEffect2 copy() {
        return new RealmwrightEffect2(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        List<Permanent> lands = game.getBattlefield().getAllActivePermanents(new FilterControlledLandPermanent(), source.getControllerId(), game);
        String choice = (String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY);
        if (you != null && choice != null) {
            for (Permanent land : lands) {
                if (land != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA && !land.getSubtype().contains(choice)) {
                                land.getSubtype().add(choice);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                boolean addAbility = true;
                                if (choice.equals("Forest")) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof GreenManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals("Plains")) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof WhiteManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals("Mountain")) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof RedManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals("Island")) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlueManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals("Swamp")) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlackManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    }
                                }
                            }
                            break;
                    }
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
