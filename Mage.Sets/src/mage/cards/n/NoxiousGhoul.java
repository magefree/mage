package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NoxiousGhoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all non-Zombie creatures");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.ZOMBIE, "Zombie");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public NoxiousGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ), filter2, false, false));
    }

    private NoxiousGhoul(final NoxiousGhoul card) {
        super(card);
    }

    @Override
    public NoxiousGhoul copy() {
        return new NoxiousGhoul(this);
    }
}
