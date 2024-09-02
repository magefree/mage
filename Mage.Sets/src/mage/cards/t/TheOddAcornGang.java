package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Grath
 */
public final class TheOddAcornGang extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrels you control");
    public static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrel");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TheOddAcornGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Squirrels you control have "{T}: Target Squirrel gets +2/+2 and gains trample until end of turn. Activate only as a sorcery."
        Ability ability = new ActivateAsSorceryActivatedAbility(new BoostTargetEffect(2, 2)
                .setText("Target Squirrel gets +2/+2"), new TapSourceCost());
        ability.addEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and gains trample until end of turn")
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, filter
        )));

        // Whenever one or more Squirrels you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1), filter, SetTargetPointer.NONE, false));
    }

    private TheOddAcornGang(final TheOddAcornGang card) {
        super(card);
    }

    @Override
    public TheOddAcornGang copy() {
        return new TheOddAcornGang(this);
    }
}
