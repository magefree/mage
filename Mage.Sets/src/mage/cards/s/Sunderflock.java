package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue.Quality;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Sunderflock extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.ELEMENTAL, "Elementals you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("non-Elemental creature");

    static {
        filter2.add(Predicates.not(SubType.ELEMENTAL.getPredicate()));
    }

    public Sunderflock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {X} less to cast, where X is the greatest mana value among Elementals you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SpellCostReductionSourceEffect(new GreatestAmongPermanentsValue(Quality.ManaValue, filter))
        ).setRuleAtTheTop(true)
        .addHint(new GreatestAmongPermanentsValue(Quality.ManaValue, filter).getHint()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, if you cast it, return all non-Elemental creatures to their owners' hands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new ReturnToHandFromBattlefieldAllEffect(filter2)
                .setText("return all non-Elemental creatures to their owners' hands"),
            false
        ).withInterveningIf(CastFromEverywhereSourceCondition.instance));
    }

    private Sunderflock(final Sunderflock card) {
        super(card);
    }

    @Override
    public Sunderflock copy() {
        return new Sunderflock(this);
    }
}
