package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwampsnareTrap extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(StaticFilters.FILTER_CREATURE_FLYING);

    public SwampsnareTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // This spell costs {1} less to cast if it targets a creature with flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -5/-3.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-5, -3)));
    }

    private SwampsnareTrap(final SwampsnareTrap card) {
        super(card);
    }

    @Override
    public SwampsnareTrap copy() {
        return new SwampsnareTrap(this);
    }
}
