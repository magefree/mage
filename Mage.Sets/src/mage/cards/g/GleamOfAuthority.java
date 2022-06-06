
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GleamOfAuthority extends CardImpl {

    public GleamOfAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each +1/+1 counter on other creatures you control
        DynamicValue amount = new CountersOnControlledCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(amount, amount, Duration.WhileOnBattlefield)
                .setText("Enchanted creature gets +1/+1 for each +1/+1 counter on other creatures you control.")
        ));

        // Enchanted creature has vigilance and "{W}, {T}: Bloster 1."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BolsterEffect(1), new ManaCostsImpl<>("{W}"));
        gainedAbility.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA).setText("and \"{W}, {T}: Bloster 1.\""));
        this.addAbility(ability);
    }

    private GleamOfAuthority(final GleamOfAuthority card) {
        super(card);
    }

    @Override
    public GleamOfAuthority copy() {
        return new GleamOfAuthority(this);
    }
}

class CountersOnControlledCount implements DynamicValue {

    static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public CountersOnControlledCount() {
    }

    public CountersOnControlledCount(final CountersOnControlledCount dynamicValue) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent enchantment = game.getPermanent(sourceAbility.getSourceId());
        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, sourceAbility.getControllerId(), game)) {
            if (!permanent.getId().equals(enchantment.getAttachedTo())) {
                count += permanent.getCounters(game).getCount(CounterType.P1P1);
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new CountersOnControlledCount(this);
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter on other creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
