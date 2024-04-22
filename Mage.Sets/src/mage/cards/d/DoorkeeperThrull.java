package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.DontCauseTriggerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoorkeeperThrull extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public DoorkeeperThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Artifacts and creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(new DontCauseTriggerEffect(filter, false, null)));
    }

    private DoorkeeperThrull(final DoorkeeperThrull card) {
        super(card);
    }

    @Override
    public DoorkeeperThrull copy() {
        return new DoorkeeperThrull(this);
    }
}
