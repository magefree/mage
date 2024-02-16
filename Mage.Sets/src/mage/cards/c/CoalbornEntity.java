package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoalbornEntity extends CardImpl {

    private static final FilterPermanentOrPlayer filter
            = new FilterPermanentOrPlayer("creature token, player, or planeswalker");

    static {
        filter.getPermanentFilter().add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        TokenPredicate.TRUE
                )
        ));
    }

    public CoalbornEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{R}: Coalborn Entity deals 1 damage to target creature token, player, or planeswalker.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);
    }

    private CoalbornEntity(final CoalbornEntity card) {
        super(card);
    }

    @Override
    public CoalbornEntity copy() {
        return new CoalbornEntity(this);
    }
}
