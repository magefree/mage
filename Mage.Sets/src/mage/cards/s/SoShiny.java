package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoShiny extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public SoShiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When So Shiny enters the battlefield, if you control a token, tap enchanted creature, then scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()).withInterveningIf(condition);
        ability.addEffect(new ScryEffect(2).concatBy(", then"));
        this.addAbility(ability.addHint(hint));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    private SoShiny(final SoShiny card) {
        super(card);
    }

    @Override
    public SoShiny copy() {
        return new SoShiny(this);
    }
}
