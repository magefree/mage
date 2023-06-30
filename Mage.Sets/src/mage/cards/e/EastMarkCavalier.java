package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EastMarkCavalier extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Goblin or Orc");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    public EastMarkCavalier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever East-Mark Cavalier deals damage to a Goblin or Orc, destroy that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new DestroyTargetEffect("destroy that creature"),
                false, false, true, filter
        ));
    }

    private EastMarkCavalier(final EastMarkCavalier card) {
        super(card);
    }

    @Override
    public EastMarkCavalier copy() {
        return new EastMarkCavalier(this);
    }
}
