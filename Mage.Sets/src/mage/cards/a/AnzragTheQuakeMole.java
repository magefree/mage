package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnzragTheQuakeMole extends CardImpl {

    public AnzragTheQuakeMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // Whenever Anzrag, the Quake-Mole becomes blocked, untap each creature you control. After this combat phase, there is an additional combat phase.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(
                new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE), false
        );
        ability.addEffect(new AdditionalCombatPhaseEffect()
                .setText("After this combat phase, there is an additional combat phase"));
        this.addAbility(ability);

        // {3}{R}{R}{G}{G}: Anzrag must be blocked each combat this turn if able.
        this.addAbility(new SimpleActivatedAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.EndOfTurn)
                .setText("{this} must be blocked each combat this turn if able"), new ManaCostsImpl<>("{3}{R}{R}{G}{G}")));
    }

    private AnzragTheQuakeMole(final AnzragTheQuakeMole card) {
        super(card);
    }

    @Override
    public AnzragTheQuakeMole copy() {
        return new AnzragTheQuakeMole(this);
    }
}
