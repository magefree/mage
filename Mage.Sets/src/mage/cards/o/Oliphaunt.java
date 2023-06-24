package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Oliphaunt extends CardImpl {

    public Oliphaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Oliphaunt attacks, another target creature you control gets +2/+0 and gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("another target creature you control gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Mountaincycling {1}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private Oliphaunt(final Oliphaunt card) {
        super(card);
    }

    @Override
    public Oliphaunt copy() {
        return new Oliphaunt(this);
    }
}
