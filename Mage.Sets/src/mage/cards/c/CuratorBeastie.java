package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuratorBeastie extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("colorless creatures");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public CuratorBeastie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Colorless creatures you control enter with two additional +1/+1 counters on them.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(2), false
        ).setText("colorless creatures you control enter with two additional +1/+1 counters on them")));

        // Whenever Curator Beastie enters or attacks, manifest dread.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ManifestDreadEffect()));
    }

    private CuratorBeastie(final CuratorBeastie card) {
        super(card);
    }

    @Override
    public CuratorBeastie copy() {
        return new CuratorBeastie(this);
    }
}
