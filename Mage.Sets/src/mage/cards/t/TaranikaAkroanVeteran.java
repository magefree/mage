package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaranikaAkroanVeteran extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TaranikaAkroanVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Taranika, Akroan Veteran attacks, untap another target creature you control. Until end of turn, that creature has base power and toughness 4/4 and gains indestructible.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect(), false);
        ability.addEffect(new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                .setText("Until end of turn, that creature has base power and toughness 4/4"));
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TaranikaAkroanVeteran(final TaranikaAkroanVeteran card) {
        super(card);
    }

    @Override
    public TaranikaAkroanVeteran copy() {
        return new TaranikaAkroanVeteran(this);
    }
}
