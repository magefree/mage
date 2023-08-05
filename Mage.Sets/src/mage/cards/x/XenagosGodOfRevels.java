package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class XenagosGodOfRevels extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public XenagosGodOfRevels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to red and green is less than seven, Xenagos isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.RG, 7))
                .addHint(DevotionCount.RG.getHint()));

        // At the beginning of combat on your turn, another target creature you control gains haste and gets +X/+X until end of turn, where X is that creature's power.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(
                        HasteAbility.getInstance(), Duration.EndOfTurn
                ).setText("another target creature you control gains haste"),
                TargetController.YOU, false, false
        );
        ability.addEffect(new XenagosGodOfRevelsEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private XenagosGodOfRevels(final XenagosGodOfRevels card) {
        super(card);
    }

    @Override
    public XenagosGodOfRevels copy() {
        return new XenagosGodOfRevels(this);
    }
}

class XenagosGodOfRevelsEffect extends OneShotEffect {

    XenagosGodOfRevelsEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "and gets +X/+X until end of turn, where X is that creature's power";
    }

    private XenagosGodOfRevelsEffect(final XenagosGodOfRevelsEffect effect) {
        super(effect);
    }

    @Override
    public XenagosGodOfRevelsEffect copy() {
        return new XenagosGodOfRevelsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }
        int power = targetCreature.getPower().getValue();
        game.addEffect(new BoostTargetEffect(
                power, power, Duration.EndOfTurn
        ).setTargetPointer(this.getTargetPointer()), source);
        return false;
    }
}
