
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Merseine extends CardImpl {

    public Merseine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Merseine enters the battlefield with three net counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.NET.createInstance(3));
        effect.setText("with three net counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // Enchanted creature doesn't untap during its controller's untap step if Merseine has a net counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepEnchantedEffect(),
                new SourceHasCounterCondition(CounterType.NET)).setText("Enchanted creature doesn't untap during its controller's untap step if Merseine has a net counter on it")));

        // Pay enchanted creature's mana cost: Remove a net counter from Merseine. Any player may activate this ability, but only if they control the enchanted creature.
        SimpleActivatedAbility ability = new MerseineActivatedAbility();
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);
    }

    private Merseine(final Merseine card) {
        super(card);
    }

    @Override
    public Merseine copy() {
        return new Merseine(this);
    }
}

class MerseineActivatedAbility extends SimpleActivatedAbility {

    public MerseineActivatedAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.NET.createInstance()), new MerseineCost());
    }

    private MerseineActivatedAbility(final MerseineActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(this.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                return super.canActivate(attachedTo.getControllerId(), game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public MerseineActivatedAbility copy() {
        return new MerseineActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "Pay enchanted creature's mana cost: Remove a net counter from Merseine. Any player may activate this ability, but only if they control the enchanted creature.";
    }
}

class MerseineCost extends CostImpl {

    public MerseineCost() {
        this.text = "Pay enchanted creature's mana cost";
    }

    public MerseineCost(final MerseineCost cost) {
        super(cost);
    }

    @Override
    public MerseineCost copy() {
        return new MerseineCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                return attachedTo.getManaCost().canPay(ability, source, controllerId, game);
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent sourcePermanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
            if (attachedTo != null) {
                paid = attachedTo.getManaCost().pay(ability, game, source, controllerId, noMana);
            }
        }
        return paid;
    }
}
