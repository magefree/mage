package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirbolgFlutist extends CardImpl {

    public FirbolgFlutist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Captivating Performance — When Firbolg Flutist enters the battlefield, gain control of target creature you don't control until end of turn. Untap that creature. It gains haste and myriad until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), false
        );
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste"));
        ability.addEffect(new GainAbilityTargetEffect(
                new MyriadAbility(), Duration.EndOfTurn
        ).setText("and myriad until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability.withFlavorWord("Captivating Performance"));
    }

    private FirbolgFlutist(final FirbolgFlutist card) {
        super(card);
    }

    @Override
    public FirbolgFlutist copy() {
        return new FirbolgFlutist(this);
    }
}
