package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhoenixDown extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 4 or less from your graveyard");
    private static final FilterPermanent filter2 = new FilterPermanent("Skeleton, Spirit, or Zombie");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
        filter2.add(Predicates.or(
                SubType.SKELETON.getPredicate(),
                SubType.SPIRIT.getPredicate(),
                SubType.ZOMBIE.getPredicate()
        ));
    }

    public PhoenixDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        // {1}{W}, {T}, Exile this artifact: Choose one--
        // * Return target creature card with mana value 4 or less from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));

        // * Exile target Skeleton, Spirit, or Zombie.
        ability.addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetPermanent(filter2)));
        this.addAbility(ability);
    }

    private PhoenixDown(final PhoenixDown card) {
        super(card);
    }

    @Override
    public PhoenixDown copy() {
        return new PhoenixDown(this);
    }
}
