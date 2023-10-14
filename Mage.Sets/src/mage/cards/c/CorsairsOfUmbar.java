package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorsairsOfUmbar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Goblin, Orc, or Pirate");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate(),
                SubType.PIRATE.getPredicate()
        ));
    }

    public CorsairsOfUmbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{U}: Target Goblin, Orc, or Pirate can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever Corsairs of Umbar deals combat damage to a player, amass Orcs 3.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AmassEffect(3, SubType.ORC), false
        ));
    }

    private CorsairsOfUmbar(final CorsairsOfUmbar card) {
        super(card);
    }

    @Override
    public CorsairsOfUmbar copy() {
        return new CorsairsOfUmbar(this);
    }
}
