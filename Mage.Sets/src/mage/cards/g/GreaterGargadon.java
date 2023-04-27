
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class GreaterGargadon extends CardImpl {

    public GreaterGargadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(9);
        this.toughness = new MageInt(7);

        // Suspend 10-{R}
        this.addAbility(new SuspendAbility(10, new ManaCostsImpl<>("{R}"), this));
        // Sacrifice an artifact, creature, or land: Remove a time counter from Greater Gargadon. Activate this ability only if Greater Gargadon is suspended.
        this.addAbility(new GreaterGargadonAbility());
    }

    private GreaterGargadon(final GreaterGargadon card) {
        super(card);
    }

    @Override
    public GreaterGargadon copy() {
        return new GreaterGargadon(this);
    }
}

class GreaterGargadonAbility extends ActivatedAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public GreaterGargadonAbility() {
        super(Zone.EXILED, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), new SacrificeTargetCost(new TargetControlledPermanent(filter)));
    }

    public GreaterGargadonAbility(final GreaterGargadonAbility ability) {
        super(ability);
    }

    @Override
    public GreaterGargadonAbility copy() {
        return new GreaterGargadonAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Card card = game.getCard(this.getSourceId());
        if (card == null || card.getCounters(game).getCount(CounterType.TIME) == 0) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only if Greater Gargadon is suspended.";
    }
}
