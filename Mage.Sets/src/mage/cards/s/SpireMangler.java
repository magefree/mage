package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpireMangler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature with flying you control");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SpireMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spire Mangler enters the battlefield, target creature with flying you control gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpireMangler(final SpireMangler card) {
        super(card);
    }

    @Override
    public SpireMangler copy() {
        return new SpireMangler(this);
    }
}
