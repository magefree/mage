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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.abilities.keyword.DefenderAbility;
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
import mage.game.permanent.token.Token;

/**
 *
 * @author cbt33
 */
public class TestamentOfFaith extends CardImpl<TestamentOfFaith> {

    public TestamentOfFaith(UUID ownerId) {
        super(ownerId, 55, "Testament of Faith", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);

        // {X}: Testament of Faith becomes an X/X Wall creature with defender until end of turn. It's still an enchantment.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TestamentOfFaithBecomesCreatureSourceEffect(new TestamentOfFaithToken(), "enchantment", Duration.EndOfTurn), new VariableManaCost()));
    }

    public TestamentOfFaith(final TestamentOfFaith card) {
        super(card);
    }

    @Override
    public TestamentOfFaith copy() {
        return new TestamentOfFaith(this);
    }
}

class TestamentOfFaithBecomesCreatureSourceEffect extends ContinuousEffectImpl<TestamentOfFaithBecomesCreatureSourceEffect> implements SourceEffect {

    protected Token token;
    protected String type;
    protected int zoneChangeCounter;

    public TestamentOfFaithBecomesCreatureSourceEffect(Token token, String type, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.type = type;
        setText();
    }

    public TestamentOfFaithBecomesCreatureSourceEffect(final TestamentOfFaithBecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public TestamentOfFaithBecomesCreatureSourceEffect copy() {
        return new TestamentOfFaithBecomesCreatureSourceEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.getAffectedObjects().add(source.getSourceId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            this.zoneChangeCounter = permanent.getZoneChangeCounter();
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getZoneChangeCounter() == this.zoneChangeCounter) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (token.getCardType().size() > 0) {
                            for (CardType t : token.getCardType()) {
                                if (!permanent.getCardType().contains(t)) {
                                    permanent.getCardType().add(t);
                                }
                            }
                        }
                        if ("".equals(type) || type == null) {
                            permanent.getSubtype().clear();
                        }
                        if (token.getSubtype().size() > 0) {
                            permanent.getSubtype().addAll(token.getSubtype());
                        }
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        if (token.getColor().hasColor()) {
                            permanent.getColor().setColor(token.getColor());
                        }
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        if (token.getAbilities().size() > 0) {
                            for (Ability ability: token.getAbilities()) {
                                permanent.addAbility(ability, game);
                            }
                        }
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        MageInt power = new MageInt(source.getManaCosts().getVariableCosts().get(0).getAmount());
                        MageInt toughness = new MageInt(source.getManaCosts().getVariableCosts().get(0).getAmount());
                        if (power != null && toughness != null) {
                            permanent.getPower().setValue(power.getValue());
                            permanent.getToughness().setValue(toughness.getValue());
                        }
                    }
            }
            return true;
        } else {
            if (duration.equals(Duration.Custom)) {
                this.discard();
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        if (type.length() > 0) {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription() + " that's still a " + this.type;
        }
        else {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription();
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}


class TestamentOfFaithToken extends Token {
    TestamentOfFaithToken() {
        super("Wall", "a X/X Wall creature with defender");
        cardType.add(CardType.CREATURE);
        this.subtype.add("Wall");
        color = ObjectColor.WHITE;
        this.addAbility(DefenderAbility.getInstance());
    }
}