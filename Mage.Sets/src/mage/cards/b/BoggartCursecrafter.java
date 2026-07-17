package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoggartCursecrafter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN, "another Goblin you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BoggartCursecrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another Goblin you control dies, this creature deals 1 damage to each opponent.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter
        ));
    }

    private BoggartCursecrafter(final BoggartCursecrafter card) {
        super(card);
    }

    @Override
    public BoggartCursecrafter copy() {
        return new BoggartCursecrafter(this);
    }
}
