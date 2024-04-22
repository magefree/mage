package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheodenKingOfRohan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Human");

    public TheodenKingOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Theoden, King of Rohan or another Human enters the battlefield under your control, target creature gains double strike until end of turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()),
                filter, false, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TheodenKingOfRohan(final TheodenKingOfRohan card) {
        super(card);
    }

    @Override
    public TheodenKingOfRohan copy() {
        return new TheodenKingOfRohan(this);
    }
}
