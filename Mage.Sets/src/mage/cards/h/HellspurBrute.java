package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellspurBrute extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("outlaws");

    static {
        filter.add(OutlawPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Outlaws you control", new PermanentsOnBattlefieldCount(filter)
    );

    public HellspurBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for outlaws
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HellspurBrute(final HellspurBrute card) {
        super(card);
    }

    @Override
    public HellspurBrute copy() {
        return new HellspurBrute(this);
    }
}
