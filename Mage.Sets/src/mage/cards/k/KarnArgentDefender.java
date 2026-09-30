package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.DontCauseTriggerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KarnArgentDefender extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public KarnArgentDefender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Artifacts and creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(new DontCauseTriggerEffect(filter, false, null)));
    }

    private KarnArgentDefender(final KarnArgentDefender card) {
        super(card);
    }

    @Override
    public KarnArgentDefender copy() {
        return new KarnArgentDefender(this);
    }
}
