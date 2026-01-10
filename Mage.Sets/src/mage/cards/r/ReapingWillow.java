package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class ReapingWillow extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ReapingWillow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}{W/B}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
            "with two -1/-1 counters on it"
        ));

        // {1}{W/B}, Remove two counters from this creature: Return target creature card with mana value 3 or less from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{1}{W/B}"));
        ability.addCost(new RemoveCountersSourceCost(2));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ReapingWillow(final ReapingWillow card) {
        super(card);
    }

    @Override
    public ReapingWillow copy() {
        return new ReapingWillow(this);
    }
}
