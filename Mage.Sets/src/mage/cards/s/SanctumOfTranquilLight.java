package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class SanctumOfTranquilLight extends CardImpl {

    public SanctumOfTranquilLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // {5}{W}: Tap target creature. This ability costs {1} less to activate for each Shrine you control.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{5}{W}"));
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each Shrine you control"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setCostAdjuster(SanctumOfTranquilLightAdjuster.instance);
        this.addAbility(ability.addHint(SanctumOfTranquilLightAdjuster.getHint()));
    }

    private SanctumOfTranquilLight(final SanctumOfTranquilLight card) {
        super(card);
    }

    @Override
    public SanctumOfTranquilLight copy() {
        return new SanctumOfTranquilLight(this);
    }
}

enum SanctumOfTranquilLightAdjuster implements CostAdjuster {
    instance;

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE);
    private static final DynamicValue count = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Shrines you control", count);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            CardUtil.reduceCost(ability, count.calculate(game, ability, null));
        }
    }
}
