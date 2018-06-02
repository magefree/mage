
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class XenagosGodOfRevels extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");
    static {
        filter.add(new AnotherPredicate());
    }

    public XenagosGodOfRevels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to red and green is less than seven, Xenagos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.R, ColoredManaSymbol.G), 7);
        effect.setText("As long as your devotion to red and green is less than seven, Xenagos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of combat on your turn, another target creature you control gains haste and gets +X/+X until end of turn, where X is that creature's power.
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("another target creature you control gains haste");
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false, false);
        ability.addEffect(new XenagosGodOfRevelsEffect());
        ability.addTarget(new TargetControlledCreaturePermanent(1,1,filter, false));
        this.addAbility(ability);
    }

    public XenagosGodOfRevels(final XenagosGodOfRevels card) {
        super(card);
    }

    @Override
    public XenagosGodOfRevels copy() {
        return new XenagosGodOfRevels(this);
    }
}

class XenagosGodOfRevelsEffect extends OneShotEffect {

    public XenagosGodOfRevelsEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "and gets +X/+X until end of turn, where X is that creature's power";
    }

    public XenagosGodOfRevelsEffect(final XenagosGodOfRevelsEffect effect) {
        super(effect);
    }

    @Override
    public XenagosGodOfRevelsEffect copy() {
        return new XenagosGodOfRevelsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            ContinuousEffect effect = new BoostTargetEffect(targetCreature.getPower().getValue(), targetCreature.getPower().getValue(), Duration.EndOfTurn);
            effect.setTargetPointer(this.getTargetPointer());
            game.addEffect(effect, source);
        }
        return false;
    }
}
