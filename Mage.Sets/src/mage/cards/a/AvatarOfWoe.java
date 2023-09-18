package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AvatarOfWoe extends CardImpl {

    protected static final DynamicValue graveyardCount = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final Condition condition = new AvatarOfWoeCondition();

    public AvatarOfWoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // If there are ten or more creature cards total in all graveyards, Avatar of Woe costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(6, condition)
                .setText("If there are ten or more creature cards total in all graveyards, this spell costs {6} less to cast"))
                .addHint(new ValueHint("Creature cards in all graveyards", graveyardCount))
        );

        // Fear
        this.addAbility(FearAbility.getInstance());

        // {T}: Destroy target creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AvatarOfWoe(final AvatarOfWoe card) {
        super(card);
    }

    @Override
    public AvatarOfWoe copy() {
        return new AvatarOfWoe(this);
    }
}

class AvatarOfWoeCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return AvatarOfWoe.graveyardCount.calculate(game, source, null) >= 10;
    }
}
