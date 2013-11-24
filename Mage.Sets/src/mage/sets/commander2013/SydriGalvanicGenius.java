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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class SydriGalvanicGenius extends CardImpl<SydriGalvanicGenius> {

    private static final FilterPermanent filter = new FilterPermanent("artifact creature");
    private static final FilterArtifactPermanent filterNonCreature = new FilterArtifactPermanent("noncreature artifact");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public SydriGalvanicGenius(UUID ownerId) {
        super(ownerId, 220, "Sydri, Galvanic Genius", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SydriGalvanicGeniusEffect(), new ManaCostsImpl("{U"));
        ability.addTarget(new TargetPermanent(filterNonCreature, true));
        this.addAbility(ability);

        // {W}{B}: Target artifact creature gains deathtouch and lifelink until end of turn.
        Effect effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, "Target artifact creature gains deathtouch");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{W}{B}"));
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, "and lifelink until end of turn"));
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);
    }

    public SydriGalvanicGenius(final SydriGalvanicGenius card) {
        super(card);
    }

    @Override
    public SydriGalvanicGenius copy() {
        return new SydriGalvanicGenius(this);
    }
}

class SydriGalvanicGeniusEffect extends ContinuousEffectImpl<SydriGalvanicGeniusEffect> {

    public SydriGalvanicGeniusEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn";
    }

    public SydriGalvanicGeniusEffect(final SydriGalvanicGeniusEffect effect) {
        super(effect);
    }

    @Override
    public SydriGalvanicGeniusEffect copy() {
        return new SydriGalvanicGeniusEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent artifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!artifact.getCardType().contains(CardType.CREATURE)) {
                        artifact.getCardType().add(CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = artifact.getManaCost().convertedManaCost();
                    artifact.getPower().setValue(cmc);
                    artifact.getToughness().setValue(cmc);
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
