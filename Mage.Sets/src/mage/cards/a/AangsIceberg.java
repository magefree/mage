package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangsIceberg extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AangsIceberg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this enchantment enters, exile up to one other target nonland permanent until this enchantment leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Waterbend {3}: Sacrifice this enchantment. If you do, scry 2.
        this.addAbility(new SimpleActivatedAbility(new DoIfCostPaid(
                new ScryEffect(2), new SacrificeSourceCost(), null, false
        ), new WaterbendCost(3)));
    }

    private AangsIceberg(final AangsIceberg card) {
        super(card);
    }

    @Override
    public AangsIceberg copy() {
        return new AangsIceberg(this);
    }
}
