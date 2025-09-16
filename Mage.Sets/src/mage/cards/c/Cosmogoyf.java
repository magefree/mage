package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cosmogoyf extends CardImpl {

    public Cosmogoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // This creature's power is equal to the number of cards you own in exile and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(CosmogoyfValue.instance)
        ).addHint(CosmogoyfValue.instance.getHint()));
    }

    private Cosmogoyf(final Cosmogoyf card) {
        super(card);
    }

    @Override
    public Cosmogoyf copy() {
        return new Cosmogoyf(this);
    }
}

enum CosmogoyfValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Card you own in exile", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState().getExile().getCardsOwned(game, sourceAbility.getControllerId()).size();
    }

    @Override
    public CosmogoyfValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "cards you own in exile";
    }

    @Override
    public String toString() {
        return "1";
    }
}
