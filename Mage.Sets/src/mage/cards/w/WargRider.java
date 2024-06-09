package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WargRider extends CardImpl {

    private static final FilterControlledCreaturePermanent filterOrcAndGoblins =
            new FilterControlledCreaturePermanent("Orcs and Goblins");

    static {
        filterOrcAndGoblins.add(
                Predicates.or(
                        SubType.ORC.getPredicate(),
                        SubType.GOBLIN.getPredicate()
                )
        );
    }

    public WargRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Other Orcs and Goblins you control have menace.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        new MenaceAbility(false),
                        Duration.WhileOnBattlefield,
                        filterOrcAndGoblins,
                        true
                )
        ));

        // At the beginning of combat on your turn, amass Orcs 2.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AmassEffect(2, SubType.ORC),
                TargetController.YOU, false
        ));
    }

    private WargRider(final WargRider card) {
        super(card);
    }

    @Override
    public WargRider copy() {
        return new WargRider(this);
    }
}
