package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeakstonesSubjugation extends CardImpl {

    public WeakstonesSubjugation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Weakstone's Subjugation's enters the battlefield, you may pay {3}. If you do, tap enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new TapEnchantedEffect("permanent"), new GenericManaCost(3)
        )));

        // Enchanted permanent doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect("permanent")));
    }

    private WeakstonesSubjugation(final WeakstonesSubjugation card) {
        super(card);
    }

    @Override
    public WeakstonesSubjugation copy() {
        return new WeakstonesSubjugation(this);
    }
}
