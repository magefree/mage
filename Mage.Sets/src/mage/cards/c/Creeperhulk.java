package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Creeperhulk extends CardImpl {

    public Creeperhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {1}{G}: Until end of turn, target creature you control has base power and toughness 5/5 and gains trample.
        Ability ability = new SimpleActivatedAbility(
                new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn)
                        .setText("Until end of turn, target creature you control has base power and toughness 5/5"),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, "and gains trample"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private Creeperhulk(final Creeperhulk card) {
        super(card);
    }

    @Override
    public Creeperhulk copy() {
        return new Creeperhulk(this);
    }
}
