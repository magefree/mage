package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.permanent.token.DragonSpiritToken;

import java.util.UUID;

/**
 * @author Grath
 */
public final class GoroGoroAndSatoru extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control that entered the battlefield this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public GoroGoroAndSatoru(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever one or more creatures you control that entered the battlefield this turn deal combat damage to a
        // player, create a 5/5 red Dragon Spirit creature token with flying.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new DragonSpiritToken()), filter
        ));

        // {1}{R}: Creatures you control gain haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), new ManaCostsImpl<>("{1}{R}")));
    }

    private GoroGoroAndSatoru(final GoroGoroAndSatoru card) {
        super(card);
    }

    @Override
    public GoroGoroAndSatoru copy() {
        return new GoroGoroAndSatoru(this);
    }
}
