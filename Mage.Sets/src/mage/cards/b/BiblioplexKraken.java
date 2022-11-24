package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiblioplexKraken extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BiblioplexKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Biblioplex Kraken enters the battlefield, scry 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(3)));

        // Whenever Biblioplex Kraken attacks, you may return another creature you control to its owner's hand. If you do, Biblioplex Kraken can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))
        )));
    }

    private BiblioplexKraken(final BiblioplexKraken card) {
        super(card);
    }

    @Override
    public BiblioplexKraken copy() {
        return new BiblioplexKraken(this);
    }
}
