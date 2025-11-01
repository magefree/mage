package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NorthernAirTemple extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SHRINE), null);
    private static final Hint hint = new ValueHint("Shrines you control", xValue);
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE, "another Shrine you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public NorthernAirTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Northern Air Temple enters, each opponent loses X life and you gain X life, where X is the number of Shrines you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(xValue)
                .setText("each opponent loses X life"));
        ability.addEffect(new GainLifeEffect(xValue).concatBy("and"));
        this.addAbility(ability.addHint(hint));

        // Whenever another Shrine you control enters, each opponent loses 1 life and you gain 1 life.
        ability = new EntersBattlefieldAllTriggeredAbility(new LoseLifeOpponentsEffect(1), filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private NorthernAirTemple(final NorthernAirTemple card) {
        super(card);
    }

    @Override
    public NorthernAirTemple copy() {
        return new NorthernAirTemple(this);
    }
}
