package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QiqirnMerchant extends CardImpl {

    public QiqirnMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {1}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {7}, {T}, Sacrifice this creature: Draw three cards. This ability costs {1} less to activate for each Town you control.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(3), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each Town you control"));
        this.addAbility(ability.setCostAdjuster(QiqirnMerchantAdjuster.instance).addHint(QiqirnMerchantAdjuster.getHint()));
    }

    private QiqirnMerchant(final QiqirnMerchant card) {
        super(card);
    }

    @Override
    public QiqirnMerchant copy() {
        return new QiqirnMerchant(this);
    }
}

enum QiqirnMerchantAdjuster implements CostAdjuster {
    instance;

    private static final DynamicValue cardsCount = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.TOWN));
    private static final Hint hint = new ValueHint("Towns you control", cardsCount);

    static Hint getHint() {
        return hint;
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        int count = cardsCount.calculate(game, ability, null);
        CardUtil.reduceCost(ability, count);
    }
}
