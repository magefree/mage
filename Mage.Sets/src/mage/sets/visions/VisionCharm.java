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
package mage.sets.visions;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceLandType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author tre3qwerty
 */
public class VisionCharm extends CardImpl {

    public VisionCharm(UUID ownerId) {
        super(ownerId, 49, "Vision Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "VIS";


        // Choose one - Target player puts the top four cards of his or her library into his or her graveyard;
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn;
        Mode mode = new Mode();
        mode.getEffects().add(new VisionCharmEffect());
        this.getSpellAbility().addMode(mode);

        // or target artifact phases out.
        mode = new Mode();
        mode.getEffects().add(new PhaseOutTargetEffect());
        mode.getTargets().add(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    public VisionCharm(final VisionCharm card) {
        super(card);
    }

    @Override
    public VisionCharm copy() {
        return new VisionCharm(this);
    }
}

class VisionCharmEffect extends ContinuousEffectImpl {
    private String targetLandType;
    private String targetBasicLandType;

     public VisionCharmEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Choose a land type and a basic land type. Each land of the first chosen type becomes the second chosen type until end of turn.";
    }

    public VisionCharmEffect(final VisionCharmEffect effect) {
        super(effect);
    }

    @Override
    public VisionCharmEffect copy() {
        return new VisionCharmEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            Choice choice = new ChoiceLandType();
            controller.choose(outcome, choice, game);
            targetLandType = choice.getChoice();

            choice = new ChoiceBasicLandType();
            controller.choose(outcome, choice, game);
            targetBasicLandType = choice.getChoice();
        }
        else {
            this.discard();
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        FilterPermanent filter = new FilterLandPermanent();
        filter.add(new SubtypePredicate(targetLandType));
        for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            //Remove all existing subtypes and replace them with the selected Basic Land Type
            land.getSubtype().removeAll(land.getSubtype());
            land.getSubtype().add(targetBasicLandType);

            /* Remove the existing abilities and replace them with the ability
               of the chosen basic land */
            land.removeAllAbilities(source.getId(), game);
            switch(targetBasicLandType) {
                case "Swamp":
                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    break;
                case "Mountain":
                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    break;
                case "Forest":
                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    break;
                case "Island":
                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    break;
                case "Plains":
                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    break;
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
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
