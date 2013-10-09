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
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class Duplicant extends CardImpl<Duplicant> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }
    public Duplicant(UUID ownerId) {
        super(ownerId, 165, "Duplicant", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Imprint - When Duplicant enters the battlefield, you may exile target nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true, "<i>Imprint - </i>");
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // As long as the exiled card is a creature card, Duplicant has that card's power, toughness, and creature types. It's still a Shapeshifter.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DuplicantContinuousEffect()));
    }

    public Duplicant(final Duplicant card) {
        super(card);
    }

    @Override
    public Duplicant copy() {
        return new Duplicant(this);
    }
}
class ExileTargetEffect extends OneShotEffect<ExileTargetEffect> {


    public ExileTargetEffect() {
        super(Outcome.Exile);
    }

    public ExileTargetEffect(final ExileTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExileTargetEffect copy() {
        return new ExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent sourcePermananent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if(sourcePermananent != null){
                sourcePermananent.imprint(permanent.getId(), game);
                sourcePermananent.addInfo("imprint", new StringBuilder("[Imprinted card - ").append(permanent.getName()).append("]").toString());
            }
            return permanent.moveToExile(null, null, source.getSourceId(), game);
        } 
        
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "you may exile target nontoken creature";
    }
}


class DuplicantContinuousEffect extends ContinuousEffectImpl<DuplicantContinuousEffect> {

    public DuplicantContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As long as the exiled card is a creature card, Duplicant has that card's power, toughness, and creature types. It's still a Shapeshifter";         
    }

    public DuplicantContinuousEffect(final DuplicantContinuousEffect effect) {
        super(effect);
        }

    @Override
    public DuplicantContinuousEffect copy() {
        return new DuplicantContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if(permanent.getImprinted().size() > 0){
                Card card = game.getCard(permanent.getImprinted().get(0));
                if(card != null && card.getCardType().contains(CardType.CREATURE))
                {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                permanent.getSubtype().addAll(card.getSubtype());
                            }

                            break;
                        case PTChangingEffects_7:
                            if (sublayer == SubLayer.SetPT_7b) {
                                 permanent.getPower().setValue(card.getPower().getValue());
                                 permanent.getToughness().setValue(card.getToughness().getValue());

                            }
                    }
                    return true;

                    
                }
            }
            
        }
        return false;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
       
    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }

}