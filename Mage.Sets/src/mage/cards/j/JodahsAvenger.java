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
package mage.cards.j;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class JodahsAvenger extends CardImpl {

    public JodahsAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Until end of turn, Jodah's Avenger gets -1/-1 and gains your choice of double strike, protection from red, vigilance, or shadow.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new JodahsAvengerEffect(), new ManaCostsImpl("{0}")));
    }

    public JodahsAvenger(final JodahsAvenger card) {
        super(card);
    }

    @Override
    public JodahsAvenger copy() {
        return new JodahsAvenger(this);
    }
}

class JodahsAvengerEffect extends ContinuousEffectImpl {

    private static final HashSet<String> choices = new HashSet<>();
    private Ability gainedAbility;

    static {
        choices.add("Double strike");
        choices.add("Protection from red");
        choices.add("Vigilance");
        choices.add("Shadow");
    }

    public JodahsAvengerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.AddAbility);
        this.staticText = "Until end of turn, {this} gets -1/-1 and gains your choice of double strike, protection from red, vigilance, or shadow";
    }

    public JodahsAvengerEffect(final JodahsAvengerEffect effect) {
        super(effect);
    }

    @Override
    public JodahsAvengerEffect copy() {
        return new JodahsAvengerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose one");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                switch (choice.getChoice()) {
                    case "Double strike":
                        gainedAbility = DoubleStrikeAbility.getInstance();
                        break;
                    case "Vigilance":
                        gainedAbility = VigilanceAbility.getInstance();
                        break;
                    case "Shadow":
                        gainedAbility = ShadowAbility.getInstance();
                        break;
                    default:
                        gainedAbility = ProtectionAbility.from(ObjectColor.RED);
                        break;
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            sourceObject.addPower(-1);
            sourceObject.addToughness(-1);
            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
