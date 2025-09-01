package mage.cards.a;

import mage.MageInt;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ArchdemonOfGreed extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "Human");

    public ArchdemonOfGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.DEMON);
        this.color.setBlack(true);

        this.nightCard = true;

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                null, new TapSourceEffect(), new SacrificeTargetCost(filter), false
        ).addOtherwiseEffect(new DamageControllerEffect(9))
                .setText("sacrifice a Human. If you can't, tap {this} and it deals 9 damage to you")));
    }

    private ArchdemonOfGreed(final ArchdemonOfGreed card) {
        super(card);
    }

    @Override
    public ArchdemonOfGreed copy() {
        return new ArchdemonOfGreed(this);
    }
}
