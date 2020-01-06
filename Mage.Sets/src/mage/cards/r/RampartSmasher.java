package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampartSmasher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Knights or Walls");

    static {
        filter.add(Predicates.or(
                SubType.KNIGHT.getPredicate(),
                SubType.WALL.getPredicate()
        ));
    }

    public RampartSmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/G}{R/G}{R/G}{R/G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Rampart Smasher can't be blocked by Knights or Walls.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));
    }

    private RampartSmasher(final RampartSmasher card) {
        super(card);
    }

    @Override
    public RampartSmasher copy() {
        return new RampartSmasher(this);
    }
}
