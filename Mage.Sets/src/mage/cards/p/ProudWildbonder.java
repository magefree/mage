package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProudWildbonder extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(TrampleAbility.class));
    }

    public ProudWildbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/G}{R/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Creatures you control with trample have "You may have this creature assign its combat damage as though it weren't blocked."
        ContinuousEffect effect = new GainAbilityControlledEffect(
                DamageAsThoughNotBlockedAbility.getInstance(), Duration.WhileOnBattlefield, filter
        );
        effect.setText("Creatures you control with trample have " +
                "\"You may have this creature assign its combat damage as though it weren't blocked.\"");
        effect.setDependedToType(DependencyType.AddingAbility);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private ProudWildbonder(final ProudWildbonder card) {
        super(card);
    }

    @Override
    public ProudWildbonder copy() {
        return new ProudWildbonder(this);
    }
}
