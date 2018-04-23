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

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class TheAntiquitiesWar extends CardImpl {

    public TheAntiquitiesWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I, II — Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new LookLibraryAndPickControllerEffect(new StaticValue(5), false, new StaticValue(1),
                        StaticFilters.FILTER_CARD_ARTIFACT_AN, Zone.LIBRARY, false, true, false, Zone.HAND, true, true, false).setBackInRandomOrder(true));

        // III — Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheAntiquitiesWarEffect());

        this.addAbility(sagaAbility);

    }

    public TheAntiquitiesWar(final TheAntiquitiesWar card) {
        super(card);
    }

    @Override
    public TheAntiquitiesWar copy() {
        return new TheAntiquitiesWar(this);
    }
}

class TheAntiquitiesWarEffect extends ContinuousEffectImpl {

    public TheAntiquitiesWarEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        this.staticText = "Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn";
    }

    public TheAntiquitiesWarEffect(final TheAntiquitiesWarEffect effect) {
        super(effect);
    }

    @Override
    public TheAntiquitiesWarEffect copy() {
        return new TheAntiquitiesWarEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, source.getControllerId(), source.getSourceId(), game)) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.isArtifact()) {
                                permanent.addCardType(CardType.ARTIFACT);
                            }
                            if (!permanent.isCreature()) {
                                permanent.addCardType(CardType.CREATURE);
                            }
                            permanent.getSubtype(game).clear();
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(5);
                            permanent.getToughness().setValue(5);
                        }
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
