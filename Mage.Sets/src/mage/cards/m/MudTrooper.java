package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class MudTrooper extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Trooper creatures you control");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public MudTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trooper creatures you control have "2: This creature gets +1/+1 until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn)
                        .setText("This creature gets +1/+1 until end of turn"),
                new GenericManaCost(2));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter, false)
                        .withForceQuotes()
        ));
    }

    private MudTrooper(final MudTrooper card) {
        super(card);
    }

    @Override
    public MudTrooper copy() {
        return new MudTrooper(this);
    }
}
