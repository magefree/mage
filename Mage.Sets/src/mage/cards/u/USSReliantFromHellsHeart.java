package mage.cards.u;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSReliantFromHellsHeart extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard(
        "creature card with mana value 4 or less from your graveyard"
    );

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public USSReliantFromHellsHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Whenever U.S.S. Reliant enters or deals combat damage to a player, return target creature card with mana value 4 or less from your graveyard to the battlefield with a finality counter on it.
        OrTriggeredAbility ability = new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()),
            false,
            "Whenever {this} enters or deals combat damage to a player, ",
            new EntersBattlefieldTriggeredAbility(null),
            new DealsCombatDamageToAPlayerTriggeredAbility(null)
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 10+
        // Flying
        // 4/3
        this.addAbility(new StationLevelAbility(10).withLevelAbility(FlyingAbility.getInstance()).withPT(4, 3));
    }

    private USSReliantFromHellsHeart(final USSReliantFromHellsHeart card) {
        super(card);
    }

    @Override
    public USSReliantFromHellsHeart copy() {
        return new USSReliantFromHellsHeart(this);
    }
}
