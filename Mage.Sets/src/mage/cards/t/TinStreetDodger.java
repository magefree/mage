package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TinStreetDodger extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("except by creatures with defender");

    static {
        filter.add(Predicates.not(new AbilityPredicate(DefenderAbility.class)));
    }

    public TinStreetDodger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {R}: Tin Street Dodger can't be blocked this turn except by creatures with defender.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")
        ));
    }

    private TinStreetDodger(final TinStreetDodger card) {
        super(card);
    }

    @Override
    public TinStreetDodger copy() {
        return new TinStreetDodger(this);
    }
}
