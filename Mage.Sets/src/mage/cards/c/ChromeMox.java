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
package mage.cards.c;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.GameLog;

/**
 *
 * @author Plopman
 */
public class ChromeMox extends CardImpl {

    public ChromeMox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // Imprint - When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChromeMoxEffect(), true));
        // {tap}: Add one mana of any of the exiled card's colors.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new ChromeMoxManaEffect(), new TapSourceCost()));
    }

    public ChromeMox(final ChromeMox card) {
        super(card);
    }

    @Override
    public ChromeMox copy() {
        return new ChromeMox(this);
    }
}

class ChromeMoxEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonartifact, nonland card");

    static {
        filter.add(Predicates.not(Predicates.or(new CardTypePredicate(CardType.LAND), new CardTypePredicate(CardType.ARTIFACT))));
    }

    public ChromeMoxEffect() {
        super(Outcome.Benefit);
        staticText = "exile a nonartifact, nonland card from your hand";
    }

    public ChromeMoxEffect(ChromeMoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card cardToImprint = null;
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (!controller.getHand().isEmpty() && controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                cardToImprint = controller.getHand().get(target.getFirstTarget(), game);
            }
            if (sourcePermanent != null) {
                if (cardToImprint != null) {
                    controller.moveCardsToExile(cardToImprint, source, game, true, source.getSourceId(), sourceObject.getIdName() + " (Imprint)");
                    sourcePermanent.imprint(cardToImprint.getId(), game);
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + GameLog.getColoredObjectIdNameForTooltip(cardToImprint) + ']'), game);
                } else {
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - None]"), game);
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public ChromeMoxEffect copy() {
        return new ChromeMoxEffect(this);
    }

}

class ChromeMoxManaEffect extends ManaEffect {

    ChromeMoxManaEffect() {
        super();
        staticText = "Add one mana of any of the exiled card's colors";
    }

    ChromeMoxManaEffect(ChromeMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public ChromeMoxManaEffect copy() {
        return new ChromeMoxManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            player.getManaPool().addMana(getMana(game, source), game, source);
            return true;

        }
        return false;
    }

    private List<Mana> netManas = new ArrayList<>(); //workaround for variable types of mana
    @Override
    public List<Mana> getNetMana(Game game, Ability source){
        produceMana(true, game, source);
        return netManas;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            List<UUID> imprinted = permanent.getImprinted();
            if (!imprinted.isEmpty()) {
                Card imprintedCard = game.getCard(imprinted.get(0));
                if (imprintedCard != null) {
                    Mana mana = new Mana();
                    ObjectColor color = imprintedCard.getColor(game);
                    if(netMana){
                        netManas = new ArrayList<>();
                        if (color == null){
                            return mana;
                        }
                        if (color.isBlack()) {
                            netManas.add(Mana.BlackMana(1));
                        }
                        if (color.isRed()) {
                            netManas.add(Mana.RedMana(1));
                        }
                        if (color.isBlue()) {
                            netManas.add(Mana.BlueMana(1));
                        }
                        if (color.isGreen()) {
                            netManas.add(Mana.GreenMana(1));
                        }
                        if (color.isWhite()) {
                            netManas.add(Mana.WhiteMana(1));
                        }
                        return mana;
                    }
                    Choice choice = new ChoiceColor(true);
                    choice.getChoices().clear();
                    choice.setMessage("Pick a mana color");
                    if (color.isBlack()) {
                        choice.getChoices().add("Black");
                    }
                    if (color.isRed()) {
                        choice.getChoices().add("Red");
                    }
                    if (color.isBlue()) {
                        choice.getChoices().add("Blue");
                    }
                    if (color.isGreen()) {
                        choice.getChoices().add("Green");
                    }
                    if (color.isWhite()) {
                        choice.getChoices().add("White");
                    }
                    if (!choice.getChoices().isEmpty()) {

                        if (choice.getChoices().size() == 1) {
                            choice.setChoice(choice.getChoices().iterator().next());
                        } else {
                            if (!player.choose(outcome, choice, game)) {
                                return null;
                            }
                        }
                        switch (choice.getChoice()) {
                            case "Black":
                                mana = Mana.BlackMana(1);//player.getManaPool().addMana(Mana.BlackMana(1), game, source);
                                break;
                            case "Blue":
                                mana = Mana.BlueMana(1);//player.getManaPool().addMana(Mana.BlueMana(1), game, source);
                                break;
                            case "Red":
                                mana = Mana.RedMana(1);//player.getManaPool().addMana(Mana.RedMana(1), game, source);
                                break;
                            case "Green":
                                mana = Mana.GreenMana(1);//player.getManaPool().addMana(Mana.GreenMana(1), game, source);
                                break;
                            case "White":
                                mana = Mana.WhiteMana(1);//player.getManaPool().addMana(Mana.WhiteMana(1), game, source);
                                break;
                            case "Colorless":
                                mana = Mana.ColorlessMana(1);//player.getManaPool().addMana(Mana.ColorlessMana(1), game, source);
                                break;
                            default:
                                break;
                        }

                    }
                    return mana;
                }
            }
        }
        return null;
    }

}
