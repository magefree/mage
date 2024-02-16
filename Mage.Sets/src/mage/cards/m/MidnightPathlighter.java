package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightPathlighter extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("except by legendary creatures");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public MidnightPathlighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Creatures you control can't be blocked except by legendary creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAllEffect(
                StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED,
                filter, Duration.WhileOnBattlefield
        )));

        // Whenever one or more creatures you control deal combat damage to a player, venture into the dungeon.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new VentureIntoTheDungeonEffect()));
    }

    private MidnightPathlighter(final MidnightPathlighter card) {
        super(card);
    }

    @Override
    public MidnightPathlighter copy() {
        return new MidnightPathlighter(this);
    }
}
