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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class ArchonOfValorsReach extends CardImpl {

    public ArchonOfValorsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{G}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Archon of Valor's Reach enters the battlefield, choose artifact, enchantment, instant, sorcery, or planeswalker.
        this.addAbility(new AsEntersBattlefieldAbility(new ArchonOfValorsReachChooseEffect()));

        // Players can't cast spells of the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchonOfValorsReachReplacementEffect()));
    }

    public ArchonOfValorsReach(final ArchonOfValorsReach card) {
        super(card);
    }

    @Override
    public ArchonOfValorsReach copy() {
        return new ArchonOfValorsReach(this);
    }
}

class ArchonOfValorsReachChooseEffect extends OneShotEffect {

    public static String VALUE_KEY = "_cardtype";

    public ArchonOfValorsReachChooseEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose artifact, enchantment, instant, sorcery, or planeswalker";
    }

    public ArchonOfValorsReachChooseEffect(final ArchonOfValorsReachChooseEffect effect) {
        super(effect);
    }

    @Override
    public ArchonOfValorsReachChooseEffect copy() {
        return new ArchonOfValorsReachChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (controller != null && mageObject != null) {
            ArchonOfValorsReachChoice choices = new ArchonOfValorsReachChoice();
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(mageObject.getName() + ":  Chosen card type is " + choices.getChoice());
                System.out.println(mageObject.getId());
                game.getState().setValue(mageObject.getId().toString() + VALUE_KEY, choices.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen card type: " + choices.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }
}

class ArchonOfValorsReachChoice extends ChoiceImpl {

    public ArchonOfValorsReachChoice() {
        super(true);
        this.choices.add("Artifact");
        this.choices.add("Enchantment");
        this.choices.add("Instant");
        this.choices.add("Sorcery");
        this.choices.add("Planeswalker");
        this.message = "Choose artifact, enchantment, instant, sorcery, or planeswalker";
    }

    public ArchonOfValorsReachChoice(final ArchonOfValorsReachChoice choice) {
        super(choice);
    }

    public static CardType getType(String ch) {
        switch (ch) {
            case "Artifact":
                return CardType.ARTIFACT;
            case "Enchantment":
                return CardType.ENCHANTMENT;
            case "Instant":
                return CardType.INSTANT;
            case "Sorcery":
                return CardType.SORCERY;
            case "Planewswalker":
                return CardType.PLANESWALKER;
            default:
                return null;
        }
    }

    @Override
    public ArchonOfValorsReachChoice copy() {
        return new ArchonOfValorsReachChoice(this);
    }

}

class ArchonOfValorsReachReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    ArchonOfValorsReachReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells of the chosen type";
    }

    ArchonOfValorsReachReplacementEffect(final ArchonOfValorsReachReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        CardType cardType = (CardType) game.getState().getValue(source.getSourceId().toString() + "_cardtype");
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null && cardType != null) {
            return "You can't cast " + cardType.toString() + " spells (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CardType cardType = ArchonOfValorsReachChoice.getType((String) game.getState().getValue(source.getSourceId().toString() + "_cardtype"));
        // spell is not on the stack yet, so we have to check the card
        Card card = game.getCard(event.getSourceId());
        return cardType != null && card != null && card.getCardType().contains(cardType);
    }

    @Override
    public ArchonOfValorsReachReplacementEffect copy() {
        return new ArchonOfValorsReachReplacementEffect(this);
    }
}
