package mage.cards.b;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KithkinGreenWhiteToken;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BrigidClachansHeart extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE);
    private static final Hint hint = new ValueHint("Other creatures you control", xValue);

    public BrigidClachansHeart(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KITHKIN, SubType.WARRIOR}, "{2}{W}",
            "Brigid, Doun's Mind",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KITHKIN, SubType.SOLDIER}, "G"
        );
        this.getLeftHalfCard().setPT(3, 2);
        this.getRightHalfCard().setPT(3, 2);

        // Whenever this creature enters or transforms into Brigid, Clachan's Heart, create a 1/1 green and white Kithkin creature token.
        this.getLeftHalfCard().addAbility(new TransformsOrEntersTriggeredAbility(
            new CreateTokenEffect(new KithkinGreenWhiteToken()), false
        ));

        // At the beginning of your first main phase, you may pay {G}. If you do, transform Brigid.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
            new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{G}"))
        ));

        // Brigid, Doun's Mind
        // {T}: Add X {G} or X {W}, where X is the number of other creatures you control.
        Ability gMana = new DynamicManaAbility(
            Mana.GreenMana(1),
            xValue,
            new TapSourceCost(),
            "add X {G}, where X is the number of other creatures you control",
            true
        ).addHint(hint);
        Ability wMana = new DynamicManaAbility(
            Mana.WhiteMana(1),
            xValue,
            new TapSourceCost(),
            "add X {W}, where X is the number of other creatures you control",
            true
        );
        this.getRightHalfCard().addAbility(gMana);
        this.getRightHalfCard().addAbility(wMana);

        // At the beginning of your first main phase, you may pay {W}. If you do, transform Brigid.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
            new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{W}"))
        ));
    }

    private BrigidClachansHeart(final BrigidClachansHeart card) {
        super(card);
    }

    @Override
    public BrigidClachansHeart copy() {
        return new BrigidClachansHeart(this);
    }
}
