package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantActivateAbilitiesAttachedEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
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
public final class HoldForQuestioning extends CardImpl {

    public HoldForQuestioning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or planeswalker
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Hold for Questioning enters the battlefield, tap enchanted permanent and investigate.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect("permanent"));
        ability.addEffect(new InvestigateEffect().concatBy("and"));
        this.addAbility(ability);

        // Enchanted permanent doesn't untap during its controller's untap step and its activated abilities can't be activated.
        ability = new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect("permanent"));
        ability.addEffect(new CantActivateAbilitiesAttachedEffect().setText("and its activated abilities can't be activated"));
        this.addAbility(ability);
    }

    private HoldForQuestioning(final HoldForQuestioning card) {
        super(card);
    }

    @Override
    public HoldForQuestioning copy() {
        return new HoldForQuestioning(this);
    }
}
