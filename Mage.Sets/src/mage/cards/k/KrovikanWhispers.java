
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class KrovikanWhispers extends CardImpl {

    public KrovikanWhispers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Cumulative upkeep-Pay {U} or {B}.
        this.addAbility(new CumulativeUpkeepAbility(new OrCost("{U} or {B}", new ManaCostsImpl<>("{U}"), new ManaCostsImpl<>("{B}"))));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // When Krovikan Whispers is put into a graveyard from the battlefield, you lose 2 life for each age counter on it.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new LoseLifeSourceControllerEffect(new MultipliedValue(new CountersSourceCount(CounterType.AGE), 2))
                .setText("you lose 2 life for each age counter on it.")
        ));
    }

    private KrovikanWhispers(final KrovikanWhispers card) {
        super(card);
    }

    @Override
    public KrovikanWhispers copy() {
        return new KrovikanWhispers(this);
    }
}
