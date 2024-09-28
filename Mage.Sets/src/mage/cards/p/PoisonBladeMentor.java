package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoisonBladeMentor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ASSASSIN, "another target Assassin you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PoisonBladeMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Poison-Blade Mentor attacks, another target Assassin you control gains deathtouch until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private PoisonBladeMentor(final PoisonBladeMentor card) {
        super(card);
    }

    @Override
    public PoisonBladeMentor copy() {
        return new PoisonBladeMentor(this);
    }
}
