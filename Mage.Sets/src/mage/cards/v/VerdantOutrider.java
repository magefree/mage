package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerdantOutrider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public VerdantOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {1}{G}: Verdant Outrider can't be blocked by creatures with power 2 or less this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn)
                        .setText("{this} can't be blocked by creatures with power 2 or less this turn"),
                new ManaCostsImpl<>("{1}{G}")
        ));
    }

    private VerdantOutrider(final VerdantOutrider card) {
        super(card);
    }

    @Override
    public VerdantOutrider copy() {
        return new VerdantOutrider(this);
    }
}
