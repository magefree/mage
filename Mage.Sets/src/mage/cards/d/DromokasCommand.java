package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DromokasCommand extends CardImpl {

    private static final FilterEnchantmentPermanent filterEnchantment = new FilterEnchantmentPermanent("an enchantment");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature to put a +1/+1 counter on it");

    public DromokasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Prevent all damage target instant or sorcery spell would deal this turn;
        this.getSpellAbility().getEffects().add(new PreventDamageByTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().getTargets().add(new TargetSpell(new FilterInstantOrSorcerySpell()));

        // or Target player sacrifices an enchantment;
        Effect effect = new SacrificeEffect(filterEnchantment, 1, "target player");
        effect.setText("Target player sacrifices an enchantment");
        Mode mode = new Mode(effect);
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);

        // Put a +1/+1 counter on target creature;
        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on target creature");
        mode = new Mode(effect);
        mode.addTarget(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().getModes().addMode(mode);

        // or Target creature you control fights target creature you don't control.
        effect = new FightTargetsEffect();
        effect.setText("Target creature you control fights target creature you don't control");
        mode = new Mode(effect);
        mode.addTarget(new TargetControlledCreaturePermanent());
        mode.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().getModes().addMode(mode);

    }

    private DromokasCommand(final DromokasCommand card) {
        super(card);
    }

    @Override
    public DromokasCommand copy() {
        return new DromokasCommand(this);
    }
}
