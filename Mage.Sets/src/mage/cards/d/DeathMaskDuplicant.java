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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class DeathMaskDuplicant extends CardImpl {

    public DeathMaskDuplicant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Imprint - {1}: Exile target creature card from your graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect().setToSourceExileZone(true), new GenericManaCost(1));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // As long as a card exiled with Death-Mask Duplicant has flying, Death-Mask Duplicant has flying. The same is true for fear, first strike, double strike, haste, landwalk, protection, and trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DeathMaskDuplicantEffect()));
    }

    public DeathMaskDuplicant(final DeathMaskDuplicant card) {
        super(card);
    }

    @Override
    public DeathMaskDuplicant copy() {
        return new DeathMaskDuplicant(this);
    }

    static class DeathMaskDuplicantEffect extends ContinuousEffectImpl {

        public DeathMaskDuplicantEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.addDependedToType(DependencyType.AddingAbility);
            staticText = "As long as a card exiled with {this} has flying, {this} has flying. The same is true for fear, first strike, double strike, haste, landwalk, protection, and trample";
        }

        public DeathMaskDuplicantEffect(final DeathMaskDuplicantEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent sourceObject = game.getPermanent(source.getSourceId());

            if (sourceObject == null) {
                return false;
            }

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
                    if (exileId != null) {
                        for (UUID cardId : game.getState().getExile().getExileZone(exileId)) {
                            Card card = game.getCard(cardId);
                            if (card != null && card.isCreature()) {
                                for (Ability ability : card.getAbilities(game)) {
                                    if (ability instanceof MageSingleton) {
                                        if (ability instanceof FlyingAbility
                                                || ability instanceof FearAbility
                                                || ability instanceof FirstStrikeAbility
                                                || ability instanceof DoubleStrikeAbility
                                                || ability instanceof HasteAbility
                                                || ability instanceof TrampleAbility) {
                                            sourceObject.addAbility(ability, source.getSourceId(), game);
                                        }
                                    } else if (ability instanceof ProtectionAbility
                                            || ability instanceof LandwalkAbility) {
                                        sourceObject.addAbility(ability, source.getSourceId(), game);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }

        @Override
        public DeathMaskDuplicantEffect copy() {
            return new DeathMaskDuplicantEffect(this);
        }
    }
}
