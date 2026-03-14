package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.AbilityResolutionCountHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class SoulbrightSeeker extends CardImpl {

    public SoulbrightSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast this spell, behold an Elemental or pay {2}.
        this.getSpellAbility().addCost(new OrCost("behold an Elemental or pay {2}",
                new BeholdCost(SubType.ELEMENTAL),
                new GenericManaCost(2)
        ));

        // {R}: Target creature you control gains trample until end of turn. If this is the third time this ability has resolved this turn, add {R}{R}{R}{R}.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}"));
        ability.addEffect(
                new IfAbilityHasResolvedXTimesEffect(
                        Outcome.PutManaInPool, 3, new BasicManaEffect(Mana.RedMana(4))
                ).setText("If this is the third time this ability has resolved this turn, add {R}{R}{R}{R}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addHint(AbilityResolutionCountHint.instance);
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private SoulbrightSeeker(final SoulbrightSeeker card) {
        super(card);
    }

    @Override
    public SoulbrightSeeker copy() {
        return new SoulbrightSeeker(this);
    }
}
