package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalevolentNoble extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("an artifact or another creature");

    static {
        filter.add(MalevolentNoblePredicate.instance);
    }

    public MalevolentNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, Sacrifice an artifact or another creature: Put a +1/+1 counter on Malevolent Noble.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private MalevolentNoble(final MalevolentNoble card) {
        super(card);
    }

    @Override
    public MalevolentNoble copy() {
        return new MalevolentNoble(this);
    }
}

enum MalevolentNoblePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject obj = input.getObject();
        if (obj.getId().equals(input.getSourceId())) {
            return obj.isArtifact(game);
        }
        return obj.isArtifact(game)
                || obj.isCreature(game);
    }
}
