package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Cocoon extends CardImpl {

    public Cocoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Cocoon enters the battlefield, tap enchanted creature and put three pupa counters on Cocoon.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.PUPA.createInstance(3)));
        this.addAbility(ability);

        // Enchanted creature doesn’t untap during your untap step if Cocoon has a pupa counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepEnchantedEffect(),
                new SourceHasCounterCondition(CounterType.PUPA)).setText("Enchanted creature doesn't untap during its controller's untap step if Cocoon has a pupa counter on it")));

        // At the beginning of your upkeep, remove a pupa counter from Cocoon. If you can’t, sacrifice it, put a +1/+1 counter on enchanted creature, and that creature gains flying.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CocoonEffect(), TargetController.YOU, false));

    }

    private Cocoon(final Cocoon card) {
        super(card);
    }

    @Override
    public Cocoon copy() {
        return new Cocoon(this);
    }
}

class CocoonEffect extends OneShotEffect {

    CocoonEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a pupa counter from {this}. If you can't, sacrifice it, put a +1/+1 counter on enchanted creature, and that creature gains flying";
    }

    CocoonEffect(final CocoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.PUPA);
            if (amount > 0) {
                permanent.removeCounters(CounterType.PUPA.createInstance(), source, game);
            } else {
                Permanent enchantedPermanent = game.getPermanent(permanent.getAttachedTo());
                permanent.sacrifice(source, game);
                if (enchantedPermanent != null) {
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
                    effect.setTargetPointer(new FixedTarget(enchantedPermanent, game));
                    effect.apply(game, source);
                    ContinuousEffect effect2 = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.Custom);
                    effect2.setTargetPointer(new FixedTarget(enchantedPermanent, game));
                    game.addEffect(effect2, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CocoonEffect copy() {
        return new CocoonEffect(this);
    }
}
