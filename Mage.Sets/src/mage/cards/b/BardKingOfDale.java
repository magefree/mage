package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.DrawExceptFirstDrawTwoReplacementEffect;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BardKingOfDale extends CardImpl {

    public BardKingOfDale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new DrawExceptFirstDrawTwoReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());

        // If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new CreateTwiceThatManyTokensEffect()));
    }

    private BardKingOfDale(final BardKingOfDale card) {
        super(card);
    }

    @Override
    public BardKingOfDale copy() {
        return new BardKingOfDale(this);
    }
}
