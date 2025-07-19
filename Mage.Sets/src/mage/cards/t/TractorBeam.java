package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TractorBeam extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Spacecraft");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    public TractorBeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Spacecraft
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect("permanent")));

        // You control enchanted permanent.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect("permanent")));

        // Enchanted permanent doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect("permanent")));
    }

    private TractorBeam(final TractorBeam card) {
        super(card);
    }

    @Override
    public TractorBeam copy() {
        return new TractorBeam(this);
    }
}
