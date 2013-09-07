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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class BowOfNylea extends CardImpl<BowOfNylea> {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public BowOfNylea(UUID ownerId) {
        super(ownerId, 153, "Bow of Nylea", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.ARTIFACT}, "{1}{G}{G}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");

        this.color.setGreen(true);

        // Attacking creatures you control have deathtouch.
        GainAbilityControlledEffect gainEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, new FilterAttackingCreature(), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainEffect));
        
        // {1}{G}, {T}: Choose one - Put a +1/+1 counter on target creature;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        // or Bow of Nylea deals 2 damage to target creature with flying;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(2));
        Target target = new TargetCreaturePermanent(filterFlying);
        target.setRequired(true);
        mode.getTargets().add(target);
        ability.addMode(mode);
        // or you gain 3 life;
        mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(3));
        ability.addMode(mode);
        // or put up to four target cards from your graveyard on the bottom of your library in any order.
        mode = new Mode();
        mode.getEffects().add(new PutCardsFromGraveyardToLibraryEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(0,4, new FilterCard()));
        ability.addMode(mode);

        this.addAbility(ability);

    }

    public BowOfNylea(final BowOfNylea card) {
        super(card);
    }

    @Override
    public BowOfNylea copy() {
        return new BowOfNylea(this);
    }
}

class PutCardsFromGraveyardToLibraryEffect extends OneShotEffect<PutCardsFromGraveyardToLibraryEffect> {

    public PutCardsFromGraveyardToLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "put up to four target cards from your graveyard on the bottom of your library in any order";
    }

    public PutCardsFromGraveyardToLibraryEffect(final PutCardsFromGraveyardToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public PutCardsFromGraveyardToLibraryEffect copy() {
        return new PutCardsFromGraveyardToLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            for (UUID cardId : this.getTargetPointer().getTargets(game, source)) {
                Card card = player.getGraveyard().get(cardId, game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                }

            }
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCard(Zone.PICK, new FilterCard("on bottom of your library (last chosen will be on bottom)"));
                target.setRequired(true);
                while (cards.size() > 1) {
                    player.choose(Outcome.Neutral, cards, target, game);
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                    }
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
