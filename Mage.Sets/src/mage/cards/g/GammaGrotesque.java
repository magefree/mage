package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class GammaGrotesque extends CardImpl {

    private static final FilterCreaturePermanent filter
        = new FilterCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public GammaGrotesque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Power-up -- {4}{G}{G}: Put three +1/+1 counters on this creature. Then draw a card for each creature you control with a counter on it.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
            new ManaCostsImpl<>("{4}{G}{G}")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))
            .setText("Then draw a card for each creature you control with a counter on it"));
        this.addAbility(ability);
    }

    private GammaGrotesque(final GammaGrotesque card) {
        super(card);
    }

    @Override
    public GammaGrotesque copy() {
        return new GammaGrotesque(this);
    }
}
