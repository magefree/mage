package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StorvaldFrostGiantJarl extends CardImpl {

    public StorvaldFrostGiantJarl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // Other creatures you control have ward {3}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(3), false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Whenever Storvald, Frost Giant Jarl enters the battlefield or attacks, choose one or both —
        // • Target creature has base power and toughness 7/7 until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new SetBasePowerToughnessTargetEffect(7, 7, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());

        // • Target creature has base power and toughness 1/1 until end of turn.
        Mode mode = new Mode(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        this.addAbility(ability);
    }

    private StorvaldFrostGiantJarl(final StorvaldFrostGiantJarl card) {
        super(card);
    }

    @Override
    public StorvaldFrostGiantJarl copy() {
        return new StorvaldFrostGiantJarl(this);
    }
}
