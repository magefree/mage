package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 * @author TheElk801
 */
public final class RhythmOfTheWild extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("Creature spells you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(Predicates.not(TokenPredicate.instance));
    }

    public RhythmOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                filter, null, Duration.WhileOnBattlefield
        )));

        // Nontoken creatures you control have riot.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(
                new RiotAbility(), new FilterCreatureSpell()).setText("Nontoken creatures you control have riot. <i>(They enter the battlefield with your choice of a +1/+1 counter or haste.)</i>"));
        ability.addEffect(new GainAbilityControlledEffect(
                new RiotAbility(), Duration.WhileOnBattlefield, filter2
        ).setText(""));
        this.addAbility(ability);
    }

    private RhythmOfTheWild(final RhythmOfTheWild card) {
        super(card);
    }

    @Override
    public RhythmOfTheWild copy() {
        return new RhythmOfTheWild(this);
    }
}
