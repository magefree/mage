package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public final class MajesticHeliopterus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR, "another target Dinosaur you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MajesticHeliopterus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Majestic Heliopterus attacks, another target Dinosaur you control gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MajesticHeliopterus(final MajesticHeliopterus card) {
        super(card);
    }

    @Override
    public MajesticHeliopterus copy() {
        return new MajesticHeliopterus(this);
    }
}
