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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public class KioraMasterOfTheDepths extends CardImpl {

    public KioraMasterOfTheDepths(UUID ownerId) {
        super(ownerId, 213, "Kiora, Master of the Depths", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Kiora");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Untap up to one target creature and up to one target land.
        LoyaltyAbility ability1 = new LoyaltyAbility(new KioraUntapEffect(), 1);
        ability1.addTarget(new TargetCreaturePermanent(0, 1, new FilterCreaturePermanent(), false));
        ability1.addTarget(new TargetLandPermanent(0, 1, new FilterLandPermanent(), false));
        this.addAbility(ability1);

        // -2: Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new LoyaltyAbility(new KioraRevealEffect(), -2));

        // -8: You get an emblem with "Whenever a creature enters the battlefield under your control, you may have it fight target creature." Then put three 8/8 blue Octopus creature tokens onto the battlefield.
        Effect effect = new CreateTokenEffect(new OctopusToken(), 3);
        effect.setText("Then put three 8/8 blue Octopus creature tokens onto the battlefield");
        LoyaltyAbility ability3 = new LoyaltyAbility(new GetEmblemEffect(new KioraMasterOfTheDepthsEmblem()), -8);
        ability3.addEffect(effect);
        this.addAbility(ability3);
    }

    public KioraMasterOfTheDepths(final KioraMasterOfTheDepths card) {
        super(card);
    }

    @Override
    public KioraMasterOfTheDepths copy() {
        return new KioraMasterOfTheDepths(this);
    }
}

class KioraUntapEffect extends OneShotEffect {

    public KioraUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "Untap up to one target creature and up to one target land";
    }

    public KioraUntapEffect(final KioraUntapEffect effect) {
        super(effect);
    }

    @Override
    public KioraUntapEffect copy() {
        return new KioraUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (firstTarget != null) {
            firstTarget.untap(game);
        }
        if (secondTarget != null) {
            return secondTarget.untap(game);
        }
        return true;
    }
}

class KioraRevealEffect extends OneShotEffect {

    public KioraRevealEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard";
    }

    public KioraRevealEffect(final KioraRevealEffect effect) {
        super(effect);
    }

    @Override
    public KioraRevealEffect copy() {
        return new KioraRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            boolean creatureCardFound = false;
            boolean landCardFound = false;
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    game.setZone(card.getId(), Zone.PICK);
                    cards.add(card);
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        creatureCardFound = true;
                    }
                    if (card.getCardType().contains(CardType.LAND)) {
                        landCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                player.revealCards(sourceObject.getName(), cards, game);
                if ((creatureCardFound || landCardFound) 
                        && player.chooseUse(Outcome.DrawCard, 
                                "Put a creature card and/or a land card into your hand?", source, game)) {
                    TargetCard target = new TargetCard(Zone.PICK, new FilterCreatureCard("creature card to put into your hand"));
                    if (creatureCardFound && player.choose(Outcome.DrawCard, cards, target, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        }
                    }

                    target = new TargetCard(Zone.PICK, new FilterLandCard("land card to put into your hand"));
                    if (landCardFound && player.choose(Outcome.DrawCard, cards, target, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        }
                    }
                }
            }
            player.moveCards(cards, Zone.PICK, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}

class KioraMasterOfTheDepthsEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");

    public KioraMasterOfTheDepthsEmblem() {
        this.setName("EMBLEM: Kiora, Master of the Depths");
        
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.COMMAND,
                new KioraFightEffect(), filter, true, SetTargetPointer.PERMANENT,
                "Whenever a creature enters the battlefield under your control, you may have it fight target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.getAbilities().add(ability);
    }
}

class KioraFightEffect extends OneShotEffect {

    KioraFightEffect() {
        super(Outcome.Damage);
    }

    KioraFightEffect(final KioraFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.getCardType().contains(CardType.CREATURE)
                && target.getCardType().contains(CardType.CREATURE)) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public KioraFightEffect copy() {
        return new KioraFightEffect(this);
    }
}

class OctopusToken extends Token {

    public OctopusToken() {
        super("Octopus", "8/8 blue Octopus creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Octopus");
        power = new MageInt(8);
        toughness = new MageInt(8);
        this.setOriginalExpansionSetCode("BFZ");
    }
}
