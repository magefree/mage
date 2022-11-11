package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KatildaDawnhartMartyr extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.VAMPIRE, "Vampires");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("permanents you control that are Spirits and/or enchantments");

    static {
        filter2.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);
    private static final Hint hint = new ValueHint("Spirits and enchantments you control", xValue);

    public KatildaDawnhartMartyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.secondSideCardClazz = mage.cards.k.KatildasRisingDawn.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Protection from Vampires
        this.addAbility(new ProtectionAbility(filter));

        // Katilda, Dawnhart Martyr's power and toughness are each equal to the number of permanents you control that are Spirits and/or enchantments.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)
        ).addHint(hint));

        // Disturb {3}{W}{W}
        this.addAbility(new DisturbAbility(this, "{3}{W}{W}"));
    }

    private KatildaDawnhartMartyr(final KatildaDawnhartMartyr card) {
        super(card);
    }

    @Override
    public KatildaDawnhartMartyr copy() {
        return new KatildaDawnhartMartyr(this);
    }
}
