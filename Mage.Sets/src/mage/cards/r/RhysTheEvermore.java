package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RhysTheEvermore extends CardImpl {

    public RhysTheEvermore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Rhys enters, another target creature you control gains persist until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(new PersistAbility()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // {W}, {T}: Remove any number of counters from target creature you control. Activate only as a sorcery.
        Ability activatedAbility = new ActivateAsSorceryActivatedAbility(
            new RemoveUpToAmountCountersEffect(Integer.MAX_VALUE), new ManaCostsImpl<>("{W}")
        );
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(activatedAbility);
    }

    private RhysTheEvermore(final RhysTheEvermore card) {
        super(card);
    }

    @Override
    public RhysTheEvermore copy() {
        return new RhysTheEvermore(this);
    }
}
