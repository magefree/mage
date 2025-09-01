package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
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
public final class EelHounds extends CardImpl {

    public EelHounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature attacks, another target creature you control gets +2/+2 and gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(2, 2).setText("another target creature you control gets +2/+2"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private EelHounds(final EelHounds card) {
        super(card);
    }

    @Override
    public EelHounds copy() {
        return new EelHounds(this);
    }
}
