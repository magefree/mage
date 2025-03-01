package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayersGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Jmlundeen
 */
public final class RiverchurnMonument extends CardImpl {

    public RiverchurnMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");
        

        // {1}, {T}: Any number of target players each mill two cards.
        Effect effect = new MillCardsTargetEffect(2);
        effect.setText("Any number of target players each mill two cards. " +
                "(Each of them puts the top two cards of their library into their graveyard.)");
        Cost cost = new CompositeCost(new TapSourceCost(), new ManaCostsImpl<>("{1}"), "{1}, {T}");
        Ability ability = new SimpleActivatedAbility(effect, cost);
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
        // Exhaust -- {2}{U}{U}, {T}: Any number of target players each mill cards equal to the number of cards in their graveyard.
        Cost exhaustCost = new CompositeCost(new TapSourceCost(), new ManaCostsImpl<>("{2}{U}{U}"), "{2}{U}{U}, {T}");
        Effect exhaustEffect = new MillCardsTargetEffect(new CardsInTargetPlayersGraveyardCount());
        exhaustEffect.setText("Any number of target players each mill cards equal to the number of cards in their graveyard.");
        Ability exhaustAbility = new ExhaustAbility(exhaustEffect, exhaustCost);
        exhaustAbility.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(exhaustAbility);
    }

    private RiverchurnMonument(final RiverchurnMonument card) {
        super(card);
    }

    @Override
    public RiverchurnMonument copy() {
        return new RiverchurnMonument(this);
    }
}
