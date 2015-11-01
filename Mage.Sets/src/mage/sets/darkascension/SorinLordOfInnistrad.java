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
package mage.sets.darkascension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public class SorinLordOfInnistrad extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public SorinLordOfInnistrad(UUID ownerId) {
        super(ownerId, 142, "Sorin, Lord of Innistrad", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Sorin");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Put a 1/1 black Vampire creature token with lifelink onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new VampireToken()), 1));

        // -2: You get an emblem with "Creatures you control get +1/+0."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SorinEmblem()), -2));

        // -6: Destroy up to three target creatures and/or other planeswalkers. Return each card put into a graveyard this way to the battlefield under your control.
        LoyaltyAbility ability = new LoyaltyAbility(new SorinLordOfInnistradEffect(), -6);
        ability.addTarget(new TargetPermanent(0, 3, filter, false));
        this.addAbility(ability);
    }

    public SorinLordOfInnistrad(final SorinLordOfInnistrad card) {
        super(card);
    }

    @Override
    public SorinLordOfInnistrad copy() {
        return new SorinLordOfInnistrad(this);
    }
}

class VampireToken extends Token {

    VampireToken() {
        super("Vampire", "a 1/1 black Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Vampire");
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }
}

class SorinEmblem extends Emblem {

    public SorinEmblem() {
        this.setName("EMBLEM: Sorin, Lord of Innistrad");
        BoostControlledEffect effect = new BoostControlledEffect(1, 0, Duration.EndOfGame);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);
    }
}

class SorinLordOfInnistradEffect extends OneShotEffect {

    public SorinLordOfInnistradEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Destroy up to three target creatures and/or other planeswalkers. Return each card put into a graveyard this way to the battlefield under your control";
    }

    public SorinLordOfInnistradEffect(final SorinLordOfInnistradEffect effect) {
        super(effect);
    }

    @Override
    public SorinLordOfInnistradEffect copy() {
        return new SorinLordOfInnistradEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : source.getTargets().get(0).getTargets()) {
            Permanent perm = game.getPermanent(targetId);
            if (perm != null) {
                perm.destroy(source.getSourceId(), game, false);
            }
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID targetId : source.getTargets().get(0).getTargets()) {
                if (game.getState().getZone(targetId) == Zone.GRAVEYARD) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), player.getId());
                    }
                }
            }
        }

        return true;
    }

}
