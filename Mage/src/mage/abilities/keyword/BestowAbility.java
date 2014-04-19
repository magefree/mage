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

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * 702.102. Bestow
 *
 * 702.102a Bestow represents two static abilities, one that functions while the card with bestow is
 * on the stack and another that functions both while it’s on stack and while it’s on the battlefield.
 * “Bestow [cost]” means “You may cast this card by paying [cost] rather than its mana cost.” and “If
 * you chose to pay this spell’s bestow cost, it becomes an Aura enchantment and gains enchant creature.
 * These effects last until one of two things happens: this spell has an illegal target as it resolves
 * and or the permanent this spell becomes, becomes unattached.” Paying a card’s bestow cost follows the
 * rules for paying alternative costs in rules 601.2b and 601.2e–g.
 *
 * 702.102b If a spell’s controller chooses to pay its bestow cost, that player chooses a legal target
 * for that Aura spell as defined by its enchant creature ability and rule 601.2c. See also rule 303.4.
 *
 * 702.102c A spell’s controller can’t choose to pay its bestow cost unless that player can choose a legal
 * target for that spell after it becomes an Aura spell.
 *
 * 702.102d As an Aura spell with bestow begins resolving, if its target is illegal, the effect making
 * it an Aura spell ends. It continues resolving as a creature spell and will be put onto the battlefield
 * under the control of the spell’s controller. This is an exception to rule 608.3a.
 *
 * 702.102e If an Aura with bestow is attached to an illegal object or player, it becomes unattached.
 * This is an exception to rule 704.5n.
 * 
 * You don’t choose whether the spell is going to be an Aura spell or not until the spell is already on
 * the stack. Abilities that affect when you can cast a spell, such as flash, will apply to the creature
 * card in whatever zone you’re casting it from. For example, an effect that said you can cast creature
 * spells as though they have flash will allow you to cast a creature card with bestow as an Aura spell
 * anytime you could cast an instant.
 *
 * On the stack, a spell with bestow is either a creature spell or an Aura spell. It’s never both.
 *
 * Unlike other Aura spells, an Aura spell with bestow isn’t countered if its target is illegal as it
 * begins to resolve. Rather, the effect making it an Aura spell ends, it loses enchant creature, it
 * returns to being an enchantment creature spell, and it resolves and enters the battlefield as an
 * enchantment creature.
 *
 * Unlike other Auras, an Aura with bestow isn’t put into its owner’s graveyard if it becomes unattached.
 * Rather, the effect making it an Aura ends, it loses enchant creature, and it remains on the
 * battlefield as an enchantment creature. It can attack (and its {T} abilities can be activated,
 * if it has any) on the turn it becomes unattached if it’s been under your control continuously,
 * even as an Aura, since your most recent turn began.
 *
 * If a permanent with bestow enters the battlefield by any method other than being cast, it will be
 * an enchantment creature. You can’t choose to pay the bestow cost and have it become an Aura.
 *
 * Auras attached to a creature don’t become tapped when the creature becomes tapped. Except in some
 * rare cases, an Aura with bestow remains untapped when it becomes unattached and becomes a creature.
 *
 *
 * @author LevelX2
 */


public class BestowAbility extends SpellAbility {

    public BestowAbility(Card card, String manaString) {
        super(new ManaCostsImpl(manaString), card.getName() + " using bestow");
        this.timing = TimingRule.SORCERY;
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.addTarget(auraTarget);
        this.addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BestowTypeChangingEffect());
        ability.setRuleVisible(false);
        card.addAbility(ability);
    }

    public BestowAbility(final BestowAbility ability) {
        super(ability);
    }

    @Override
    public BestowAbility copy() {
        return new BestowAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Bestow " + getManaCostsToPay().getText()+ " <i>(If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)</i>";
    }

}

class BestowTypeChangingEffect extends ContinuousEffectImpl<BestowTypeChangingEffect> implements SourceEffect {

    public BestowTypeChangingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
    }

    public BestowTypeChangingEffect(final BestowTypeChangingEffect effect) {
        super(effect);
    }

    @Override
    public BestowTypeChangingEffect copy() {
        return new BestowTypeChangingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (permanent.getAttachedTo() == null) {
                            if (permanent.getSubtype().contains("Aura")) {
                                permanent.getSubtype().remove("Aura");
                            }
                        } else {
                            permanent.getCardType().remove(CardType.CREATURE);
                            permanent.getSubtype().clear();
                            if (!permanent.getSubtype().contains("Aura")) {

                                permanent.getSubtype().add("Aura");
                            }
                        }
                    }
                    break;
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
        return layer == Layer.TypeChangingEffects_4;
    }

}
