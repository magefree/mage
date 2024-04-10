package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ZombieRogueToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GisaTheHellraiser extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Skeletons and Zombies");

    static {
        filter.add(Predicates.or(SubType.SKELETON.getPredicate(), SubType.ZOMBIE.getPredicate()));
    }

    public GisaTheHellraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward--{2}, Pay 2 life.
        this.addAbility(new WardAbility(new CompositeCost(
                new GenericManaCost(2), new PayLifeCost(2), "{2}, Pay 2 life"
        ), false));

        // Skeletons and Zombies you control get +1/+1 and have menace.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        ).setText("and have menace"));
        this.addAbility(ability);

        // Whenever you commit a crime, create two tapped 2/2 blue and black Zombie Rogue creature tokens. This ability triggers only once each turn.
        ability = new CommittedCrimeTriggeredAbility(new CreateTokenEffect(new ZombieRogueToken(), 2, true)).setTriggersOnceEachTurn(true);
        this.addAbility(ability);
    }

    private GisaTheHellraiser(final GisaTheHellraiser card) {
        super(card);
    }

    @Override
    public GisaTheHellraiser copy() {
        return new GisaTheHellraiser(this);
    }
}
