package mage.cards.m;

import java.util.Collection;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author rullinoiz
 */
public final class MoriaScavenger extends CardImpl {

    public MoriaScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1),
                new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));

        // If the discarded card was a creature card, amass Orcs 1.
        ability.addEffect(new ConditionalOneShotEffect(
                new AmassEffect(1, SubType.ORC),
                MoriaScavengerCondition.instance
        ));

        this.addAbility(ability);
    }

    private MoriaScavenger(final MoriaScavenger card) {
        super(card);
    }

    @Override
    public MoriaScavenger copy() {
        return new MoriaScavenger(this);
    }
}

enum MoriaScavengerCondition implements Condition {
    instance;

    // code "somewhat" stolen from Necromancers Stockpile
    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.castStream(source.getCosts().stream(), DiscardTargetCost.class)
                .map(DiscardTargetCost::getCards)
                .flatMap(Collection::stream)
                .anyMatch(card -> card.isCreature(game));
    }

    @Override
    public String toString() { return "the discarded card was a creature card"; }
}