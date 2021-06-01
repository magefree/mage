package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchfiendOfSorrows extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public ArchfiendOfSorrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Archfiend of Sorrows enters the battlefield, creatures your opponents control get -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, false
        )));

        // Unearth {3}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private ArchfiendOfSorrows(final ArchfiendOfSorrows card) {
        super(card);
    }

    @Override
    public ArchfiendOfSorrows copy() {
        return new ArchfiendOfSorrows(this);
    }
}
