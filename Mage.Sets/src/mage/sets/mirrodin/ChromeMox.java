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

import java.util.List;
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
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
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

    public ChromeMox(UUID ownerId) {
        super(ownerId, 152, "Chrome Mox", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "MRD";

        // Imprint - When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChromeMoxEffect(), true));
        // {tap}: Add one mana of any of the exiled card's colors to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new ChromeMoxManaEffect(), new TapSourceCost()));
    }

    public ChromeMox(final ChromeMox card) {
        super(card);
    }

    @java.lang.Override
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

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card cardToImprint = null;
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (controller.getHand().size() > 0 && controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                cardToImprint = controller.getHand().get(target.getFirstTarget(), game);
            }
            if (sourcePermanent != null) {
                if (cardToImprint != null) {
                    controller.moveCardsToExile(cardToImprint, source, game, true, source.getSourceId(), sourceObject.getIdName() + " (Imprint)");
                    sourcePermanent.imprint(cardToImprint.getId(), game);
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + GameLog.getColoredObjectIdNameForTooltip(cardToImprint) + "]"), game);
                } else {
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - None]"), game);
                }
            }
            return true;

        }
        return false;
    }

    @java.lang.Override
    public ChromeMoxEffect copy() {
        return new ChromeMoxEffect(this);
    }

}

class ChromeMoxManaEffect extends ManaEffect {

    ChromeMoxManaEffect() {
        super();
        staticText = "Add one mana of any of the exiled card's colors to your mana pool";
    }

    ChromeMoxManaEffect(ChromeMoxManaEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public ChromeMoxManaEffect copy() {
        return new ChromeMoxManaEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            List<UUID> imprinted = permanent.getImprinted();
            if (imprinted.size() > 0) {
                Card imprintedCard = game.getCard(imprinted.get(0));
                if (imprintedCard != null) {
                    Choice choice = new ChoiceImpl(true);
                    choice.setMessage("Pick a mana color");
                    ObjectColor color = imprintedCard.getColor(game);
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

                    if (choice.getChoices().size() > 0) {
                        Mana mana = new Mana();
                        if (choice.getChoices().size() == 1) {
                            choice.setChoice(choice.getChoices().iterator().next());
                        } else {
                            player.choose(outcome, choice, game);
                        }
                        if (choice.getChoice().equals("Black")) {
                            player.getManaPool().addMana(Mana.BlackMana, game, source);
                        } else if (choice.getChoice().equals("Blue")) {
                            player.getManaPool().addMana(Mana.BlueMana, game, source);
                        } else if (choice.getChoice().equals("Red")) {
                            player.getManaPool().addMana(Mana.RedMana, game, source);
                        } else if (choice.getChoice().equals("Green")) {
                            player.getManaPool().addMana(Mana.GreenMana, game, source);
                        } else if (choice.getChoice().equals("White")) {
                            player.getManaPool().addMana(Mana.WhiteMana, game, source);
                        } else if (choice.getChoice().equals("Colorless")) {
                            player.getManaPool().addMana(Mana.ColorlessMana, game, source);
                        }
                        checkToFirePossibleEvents(mana, game, source);
                        player.getManaPool().addMana(mana, game, source);
                    }
                }
            }
        }
        return true;
    }

    @java.lang.Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}
