package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RocksteadyCrashCourser extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.BOAR);

    public RocksteadyCrashCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Rocksteady can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));

        // Boars you control can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(
            new CantBeBlockedByMoreThanOneAllEffect(filter)
        ));

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RocksteadyCrashCourser(final RocksteadyCrashCourser card) {
        super(card);
    }

    @Override
    public RocksteadyCrashCourser copy() {
        return new RocksteadyCrashCourser(this);
    }
}
