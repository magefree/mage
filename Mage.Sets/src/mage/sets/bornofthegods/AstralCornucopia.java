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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AstralCornucopia extends CardImpl<AstralCornucopia> {

    public AstralCornucopia(UUID ownerId) {
        super(ownerId, 157, "Astral Cornucopia", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{X}{X}{X}");
        this.expansionSetCode = "BNG";

        // Astral Cornucopia enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AstralCornucopiaEffect(), "with X charge counters on it"));

        // {T}: Choose a color. Add one mana of that color to your mana pool for each charge counter on Astral Cornucopia.
        Ability ability = new AstralCornucopiaManaAbility();
        Choice choice = new ChoiceColor();
        choice.setMessage("Choose a color to add mana of that color");
        ability.addChoice(choice);
        this.addAbility(ability);
    }

    public AstralCornucopia(final AstralCornucopia card) {
        super(card);
    }

    @Override
    public AstralCornucopia copy() {
        return new AstralCornucopia(this);
    }
}

class AstralCornucopiaEffect extends OneShotEffect<AstralCornucopiaEffect> {
    public AstralCornucopiaEffect() {
        super(Outcome.Benefit);
    }

    public AstralCornucopiaEffect(final AstralCornucopiaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                int amount = ((SpellAbility) obj).getManaCostsToPay().getX();
                if (amount > 0) {
                    permanent.addCounters(CounterType.CHARGE.createInstance(amount), game);
                }
            }
        }
        return true;
    }

    @Override
    public AstralCornucopiaEffect copy() {
        return new AstralCornucopiaEffect(this);
    }
}

class AstralCornucopiaManaAbility extends ManaAbility<AstralCornucopiaManaAbility> {

    public AstralCornucopiaManaAbility() {
        super(Zone.BATTLEFIELD, new AstralCornucopiaManaEffect(), new TapSourceCost());
    }

    public AstralCornucopiaManaAbility(final AstralCornucopiaManaAbility ability) {
        super(ability);
    }

    @Override
    public AstralCornucopiaManaAbility copy() {
        return new AstralCornucopiaManaAbility(this);
    }

    @Override
    public Mana getNetMana(Game game) {
        if (game == null) {
            return new Mana();
        }
        return new Mana(((AstralCornucopiaManaEffect)this.getEffects().get(0)).computeMana(game, this));
    }
}


class AstralCornucopiaManaEffect extends ManaEffect {

    private final Mana computedMana;

    public AstralCornucopiaManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color. Add one mana of that color to your mana pool for each charge counter on {this}";
    }

    public AstralCornucopiaManaEffect(final AstralCornucopiaManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public AstralCornucopiaManaEffect copy() {
        return new AstralCornucopiaManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        computeMana(game, source);
        game.getPlayer(source.getControllerId()).getManaPool().addMana(computedMana, game, source);
        return true;
    }

    public Mana computeMana(Game game, Ability source){
        this.computedMana.clear();
        if (!source.getChoices().isEmpty()) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            if (choice != null && choice instanceof ChoiceColor && choice.getChoice() != null) {
                String color = choice.getChoice();
                int counters = sourcePermanent.getCounters().getCount(CounterType.CHARGE.getName());
                if (color.equals("Red")) {
                    computedMana.setRed(counters);
                } else if (color.equals("Blue")) {
                    computedMana.setBlue(counters);
                } else if (color.equals("White")) {
                    computedMana.setWhite(counters);
                } else if (color.equals("Black")) {
                    computedMana.setBlack(counters);
                } else if (color.equals("Green")) {
                    computedMana.setGreen(counters);
                }
            }
        }
        return computedMana;
    }
}
