package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class DropkickBomber extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.GOBLIN, "Goblins");

    private static final FilterControlledCreaturePermanent filter2
            = new FilterControlledCreaturePermanent(SubType.GOBLIN, "another target Goblin you control");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public DropkickBomber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Goblins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield,
                        filter, true
                )
        ));

        // {R}: Until end of turn, another target Goblin you control gains flying and "When this creature deals combat damage, sacrifice it."
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                        .setText("Until end of turn, another target Goblin you control gains flying"),
                new ManaCostsImpl<>("{R}")
        );
        ability.addEffect(
                new GainAbilityTargetEffect(
                        new DealsCombatDamageTriggeredAbility(new SacrificeSourceEffect(), false),
                        Duration.EndOfTurn
                ).setText("and \"When this creature deals combat damage, sacrifice it.\"")
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private DropkickBomber(final DropkickBomber card) {
        super(card);
    }

    @Override
    public DropkickBomber copy() {
        return new DropkickBomber(this);
    }
}
