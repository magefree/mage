package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritClericToken;

/**
 *
 * @author weirddan455
 */
public final class HallowedHaunting extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public HallowedHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // As long as you control seven or more enchantments, creatures you control have flying and vigilance.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(new CompoundAbility(FlyingAbility.getInstance(), VigilanceAbility.getInstance()), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES),
                HallowedHauntingCondition.instance,
                "As long as you control seven or more enchantments, creatures you control have flying and vigilance"
        )));

        // Whenever you cast an enchantment spell, create a white Spirit Cleric creature token with "This creature's power and toughness are each equal to the number of Spirits you control."
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new SpiritClericToken()), filter, false));
    }

    private HallowedHaunting(final HallowedHaunting card) {
        super(card);
    }

    @Override
    public HallowedHaunting copy() {
        return new HallowedHaunting(this);
    }
}

enum HallowedHauntingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int enchantments = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getCardType(game).contains(CardType.ENCHANTMENT)) {
                enchantments++;
                if (enchantments >= 7) {
                    return true;
                }
            }
        }
        return false;
    }
}
