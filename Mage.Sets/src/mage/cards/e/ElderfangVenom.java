package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElderfangVenom extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "attacking Elves");

    static {
        filter.add(AttackingPredicate.instance);
    }

    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.ELF, "an Elf you control");

    public ElderfangVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");

        // Attacking Elves you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever an Elf you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false, filter2
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ElderfangVenom(final ElderfangVenom card) {
        super(card);
    }

    @Override
    public ElderfangVenom copy() {
        return new ElderfangVenom(this);
    }
}
