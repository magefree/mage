package mage.cards.n;

import mage.MageInt;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimbleclawAdept extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other target permanents");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public NimbleclawAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Bigby's Hand — {T}: Untap two other target permanents. Activate only as a sorcery and only once each turn.
        ActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost()
        );
        ability.setTiming(TimingRule.SORCERY);
        ability.addTarget(new TargetPermanent(2, filter));
        this.addAbility(ability.withFlavorWord("Bigby's Hand"));
    }

    private NimbleclawAdept(final NimbleclawAdept card) {
        super(card);
    }

    @Override
    public NimbleclawAdept copy() {
        return new NimbleclawAdept(this);
    }
}
