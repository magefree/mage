package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BanishToAnotherUniverse extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("historic permanents");
    private static final Hint hint = new ValueHint("historic permanents you control", new PermanentsOnBattlefieldCount(filter));

    static {
        filter.add(HistoricPredicate.instance);
    }

    public BanishToAnotherUniverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Affinity for historic permanents
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter))
                .addHint(hint));

        // When Banish to Another Universe enters the battlefield, exile target nonland permanent an opponent controls until Banish to Another Universe leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private BanishToAnotherUniverse(final BanishToAnotherUniverse card) {
        super(card);
    }

    @Override
    public BanishToAnotherUniverse copy() {
        return new BanishToAnotherUniverse(this);
    }
}
