package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HamletGlutton extends CardImpl {

    public HamletGlutton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Bargain
        this.addAbility(new BargainAbility());

        // This spell costs {2} less to cast if it's bargained.
        this.getSpellAbility().addEffect(new InfoEffect("this spell costs {2} less to cast if it's bargained"));
        this.getSpellAbility().setCostAdjuster(HamletGluttonAdjuster.instance);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Hamlet Glutton enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private HamletGlutton(final HamletGlutton card) {
        super(card);
    }

    @Override
    public HamletGlutton copy() {
        return new HamletGlutton(this);
    }
}

enum HamletGluttonAdjuster implements CostAdjuster {
    instance;

    private static OptionalAdditionalCost bargainCost = BargainAbility.makeBargainCost();

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (BargainedCondition.instance.apply(game, ability)
                || (game.inCheckPlayableState() && bargainCost.canPay(ability, null, ability.getControllerId(), game))) {
            CardUtil.reduceCost(ability, 2);
        }
    }
}
