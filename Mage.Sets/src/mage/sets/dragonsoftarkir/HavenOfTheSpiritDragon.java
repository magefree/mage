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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class HavenOfTheSpiritDragon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon creature card or Ugin planeswalker card from your graveyard");

    static {
        filter.add(Predicates.or(new DragonCreatureCardPredicate(),
                new UginPlaneswalkerCardPredicate()));
    }

    public HavenOfTheSpiritDragon(UUID ownerId) {
        super(ownerId, 249, "Haven of the Spirit Dragon", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DTK";

        // {T}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {T}: add one mana of any color to your mana pool. Spend this mana only to cast a Dragon creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new HavenOfTheSpiritManaBuilder(), true));

        // {2}, {T}, Sacrifice Haven of the Spirit Dragon: Return target Dragon creature card or Ugin planeswalker card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    public HavenOfTheSpiritDragon(final HavenOfTheSpiritDragon card) {
        super(card);
    }

    @Override
    public HavenOfTheSpiritDragon copy() {
        return new HavenOfTheSpiritDragon(this);
    }
}

class HavenOfTheSpiritManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new HavenOfTheSpiritConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Dragon creature spell.";
    }
}

class HavenOfTheSpiritConditionalMana extends ConditionalMana {

    HavenOfTheSpiritConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a Dragon creature spell.";
        addCondition(new HavenOfTheSpiritManaCondition());
    }
}

class HavenOfTheSpiritManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source.getSourceId());
            if (object.hasSubtype("Dragon")
                    && object.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
}

class DragonCreatureCardPredicate implements Predicate<Card> {

    public DragonCreatureCardPredicate() {
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getCardType().contains(CardType.CREATURE)
                && input.getSubtype().contains("Dragon");
    }

    @Override
    public String toString() {
        return "";
    }
}

class UginPlaneswalkerCardPredicate implements Predicate<Card> {

    public UginPlaneswalkerCardPredicate() {
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getCardType().contains(CardType.PLANESWALKER)
                && input.getName().contains("Ugin, the Spirit Dragon");
    }

    @Override
    public String toString() {
        return "";
    }
}
