package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HydrasGrowth extends CardImpl {

    public HydrasGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Hydra's Growth enters the battlefield, put a +1/+1 counter on enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "enchanted creature"), false));
        // At the beginning of your upkeep, double the number of +1/+1 counters on enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new HydrasGrowthDoubleEffect(),
                TargetController.YOU, false));
    }

    private HydrasGrowth(final HydrasGrowth card) {
        super(card);
    }

    @Override
    public HydrasGrowth copy() {
        return new HydrasGrowth(this);
    }
}

class HydrasGrowthDoubleEffect extends OneShotEffect {

    HydrasGrowthDoubleEffect() {
        super(Outcome.BoostCreature);
        staticText = "double the number of +1/+1 counters on enchanted creature";
    }

    HydrasGrowthDoubleEffect(final HydrasGrowthDoubleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null) {
                int amount = attachedTo.getCounters(game).getCount(CounterType.P1P1);
                if (amount > 0) {
                    attachedTo.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public HydrasGrowthDoubleEffect copy() {
        return new HydrasGrowthDoubleEffect(this);
    }
}
