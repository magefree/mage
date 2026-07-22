package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ThorinKingOfDurinsFolk extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DWARF, "Dwarves");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("artifact tokens you control");

    static {
        filter2.add(CardType.ARTIFACT.getPredicate());
        filter2.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);
    private static final Hint hint = new ValueHint("Artifact tokens you control", xValue);


    public ThorinKingOfDurinsFolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Thorin or another Dwarf you control enters, create a Treasure token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new CreateTokenEffect(new TreasureToken()), filter, false, true
        ));

        // Other Dwarves you control get +1/+0 for each artifact token you control.
        this.addAbility(new SimpleStaticAbility(
            new BoostControlledEffect(
                xValue, StaticValue.get(0),
                Duration.WhileOnBattlefield, filter, true
            ).setText("other Dwarves you control get +1/+0 for each artifact token you control")
        ).addHint(hint));
    }

    private ThorinKingOfDurinsFolk(final ThorinKingOfDurinsFolk card) {
        super(card);
    }

    @Override
    public ThorinKingOfDurinsFolk copy() {
        return new ThorinKingOfDurinsFolk(this);
    }
}
