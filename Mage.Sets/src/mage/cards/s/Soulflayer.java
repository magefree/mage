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
package mage.sets.fatereforged;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class Soulflayer extends CardImpl {

    public Soulflayer(UUID ownerId) {
        super(ownerId, 84, "Soulflayer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Demon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Delve
        this.addAbility(new DelveAbility());

        // If a creature card with flying was exiled with Soulflayer's delve ability, Soulflayer has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, reach, trample, and vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SoulflayerEffect()));

    }

    public Soulflayer(final Soulflayer card) {
        super(card);
    }

    @Override
    public Soulflayer copy() {
        return new Soulflayer(this);
    }
}


class SoulflayerEffect extends ContinuousEffectImpl implements SourceEffect {

    private Set<Ability> abilitiesToAdd;
    private MageObjectReference objectReference = null;

    public SoulflayerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "If a creature card with flying was exiled with {this}'s delve ability, {this} has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, reach, trample, and vigilance";
        abilitiesToAdd = null;
    }

    public SoulflayerEffect(final SoulflayerEffect effect) {
        super(effect);
        if (effect.abilitiesToAdd != null) {
            this.abilitiesToAdd = new HashSet<>();
            this.abilitiesToAdd.addAll(effect.abilitiesToAdd);
        }
        this.objectReference = effect.objectReference;
    }

    @Override
    public SoulflayerEffect copy() {
        return new SoulflayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (objectReference == null || !objectReference.refersTo(permanent, game)) {
                abilitiesToAdd = new HashSet<>();
                this.objectReference = new MageObjectReference(permanent, game);
                String keyString = CardUtil.getCardZoneString("delvedCards", source.getSourceId(), game, true);
                List<Card> delvedCards = (List<Card>) game.getState().getValue(keyString);
                if (delvedCards != null) {
                    for(Card card: delvedCards) {
                        if (!card.getCardType().contains(CardType.CREATURE)) {
                            continue;
                        }
                        for (Ability cardAbility: card.getAbilities()) {
                            if (cardAbility instanceof FlyingAbility) {
                                abilitiesToAdd.add(FlyingAbility.getInstance());
                            }
                            if (cardAbility instanceof FirstStrikeAbility) {
                                abilitiesToAdd.add(FirstStrikeAbility.getInstance());
                            }
                            if (cardAbility instanceof DoubleStrikeAbility) {
                                abilitiesToAdd.add(DoubleStrikeAbility.getInstance());
                            }
                            if (cardAbility instanceof DeathtouchAbility) {
                                abilitiesToAdd.add(DeathtouchAbility.getInstance());
                            }
                            if (cardAbility instanceof HasteAbility) {
                                abilitiesToAdd.add(HasteAbility.getInstance());
                            }
                            if (cardAbility instanceof HexproofAbility) {
                                abilitiesToAdd.add(HexproofAbility.getInstance());
                            }
                            if (cardAbility instanceof IndestructibleAbility) {
                                abilitiesToAdd.add(IndestructibleAbility.getInstance());
                            }
                            if (cardAbility instanceof LifelinkAbility) {
                                abilitiesToAdd.add(LifelinkAbility.getInstance());
                            }
                            if (cardAbility instanceof ReachAbility) {
                                abilitiesToAdd.add(ReachAbility.getInstance());
                            }
                            if (cardAbility instanceof TrampleAbility) {
                                abilitiesToAdd.add(TrampleAbility.getInstance());
                            }
                            if (cardAbility instanceof VigilanceAbility) {
                                abilitiesToAdd.add(VigilanceAbility.getInstance());
                            }
                        }
                    }
                }
            }
            for (Ability ability: abilitiesToAdd) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
            return true;
        } else {
            if (abilitiesToAdd != null) {
                abilitiesToAdd = null;
            }
        }
        return false;
    }
}
