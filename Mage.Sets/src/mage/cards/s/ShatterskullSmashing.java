package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.TargetAmount;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatterskullSmashing extends ModalDoubleFacedCard {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public ShatterskullSmashing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{X}{R}{R}",
                "Shatterskull, the Hammer Pass", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Shatterskull Smashing
        // Sorcery

        // Shatterskull Smashing deals X damage divided as you choose among up to two target creatures and/or planeswalkers. If X is 6 or more, Shatterskull Smashing deals twice X damage divided as you choose among them instead.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageMultiEffect(xValue), new DamageMultiEffect(GetXValue.instance),
                ShatterskullSmashingCondition.instance, "{this} deals X damage divided as you choose " +
                "among up to two target creatures and/or planeswalkers. If X is 6 or more, " +
                "{this} deals twice X damage divided as you choose among them instead."
        ));
        this.getLeftHalfCard().getSpellAbility().setTargetAdjuster(ShatterskullSmashingAdjuster.instance);

        // 2.
        // Shatterskull, the Hammer Pass
        // Land

        // As Shatterskull, the Hammer Pass enters the battlefield, you may pay 3 life. If you don't, it enters tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters tapped"
        ));

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private ShatterskullSmashing(final ShatterskullSmashing card) {
        super(card);
    }

    @Override
    public ShatterskullSmashing copy() {
        return new ShatterskullSmashing(this);
    }
}

enum ShatterskullSmashingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostsTag(game, source, "X", 0) >= 6;
    }
}

enum ShatterskullSmashingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        TargetAmount target;
        if (CardUtil.getSourceCostsTag(game, ability, "X", 0) >= 6) {
            target = new TargetCreatureOrPlaneswalkerAmount(2 * CardUtil.getSourceCostsTag(game, ability, "X", 0), 0, 2);
        } else {
            target = new TargetCreatureOrPlaneswalkerAmount(CardUtil.getSourceCostsTag(game, ability, "X", 0), 0, 2);
        }
        ability.addTarget(target);
    }
}
