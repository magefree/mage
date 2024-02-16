package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenasiEnforcers extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control named Genasi Enforcers");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new NamePredicate("Genasi Enforcers"));
    }

    public GenasiEnforcers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Myriad
        this.addAbility(new MyriadAbility());

        // {1}{R}: Creatures you control named Genasi Enforcers get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl<>("{1}{R}")));
    }

    private GenasiEnforcers(final GenasiEnforcers card) {
        super(card);
    }

    @Override
    public GenasiEnforcers copy() {
        return new GenasiEnforcers(this);
    }
}
