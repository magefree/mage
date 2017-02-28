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
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author psykad
 */
public class CairnWanderer extends CardImpl {

    public CairnWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());

        // As long as a creature card with flying is in a graveyard, Cairn Wanderer has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CairnWandererEffect()));
    }

    public CairnWanderer(final CairnWanderer card) {
        super(card);
    }

    @Override
    public CairnWanderer copy() {
        return new CairnWanderer(this);
    }

    static class CairnWandererEffect extends ContinuousEffectImpl {

        public CairnWandererEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            staticText = "As long as a creature card with flying is in a graveyard, {this} has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.";
        }

        public CairnWandererEffect(final CairnWandererEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());

            if (sourcePermanent == null) {
                return false;
            }

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);

                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(game)) {
                        if (card.getCardType().contains(CardType.CREATURE)) {
                            for (Ability ability : card.getAbilities(game)) {
                                if (ability instanceof MageSingleton) {
                                    if (ability instanceof FlyingAbility
                                            || ability instanceof FearAbility
                                            || ability instanceof FirstStrikeAbility
                                            || ability instanceof DoubleStrikeAbility
                                            || ability instanceof DeathtouchAbility
                                            || ability instanceof HasteAbility
                                            || ability instanceof LifelinkAbility
                                            || ability instanceof ReachAbility
                                            || ability instanceof TrampleAbility
                                            || ability instanceof ShroudAbility
                                            || ability instanceof VigilanceAbility) {
                                        sourcePermanent.addAbility(ability, game);
                                    }
                                } else if (ability instanceof ProtectionAbility
                                        || ability instanceof LandwalkAbility) {
                                    sourcePermanent.addAbility(ability, game);
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }

        @Override
        public CairnWandererEffect copy() {
            return new CairnWandererEffect(this);
        }
    }
}
