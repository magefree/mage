package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackMarketTycoon extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TREASURE);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
            "Treasures you control", new PermanentsOnBattlefieldCount(filter)
    );

    public BlackMarketTycoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, Black Market Tycoon deals 2 damage to you for each Treasure you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageControllerEffect(xValue), TargetController.YOU, false
        ).addHint(hint));

        // {T}: Create a Treasure token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new TapSourceCost()));
    }

    private BlackMarketTycoon(final BlackMarketTycoon card) {
        super(card);
    }

    @Override
    public BlackMarketTycoon copy() {
        return new BlackMarketTycoon(this);
    }
}
