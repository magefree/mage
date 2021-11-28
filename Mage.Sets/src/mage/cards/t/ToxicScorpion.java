package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToxicScorpion extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ToxicScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Toxic Scorpion enters the battlefield, another target creature you control gains deathtouch until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ToxicScorpion(final ToxicScorpion card) {
        super(card);
    }

    @Override
    public ToxicScorpion copy() {
        return new ToxicScorpion(this);
    }
}
