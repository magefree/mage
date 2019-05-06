package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostOrderOfJarkeld extends CardImpl {

    public LostOrderOfJarkeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As Lost Order of Jarkeld enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // Lost Order of Jarkeld's power and toughness are each equal to 1 plus the number of creatures the chosen player controls.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetPowerToughnessSourceEffect(
                        LostOrderOfJarkeldValue.instance, Duration.Custom, SubLayer.CharacteristicDefining_7a
                )
        ));
    }

    private LostOrderOfJarkeld(final LostOrderOfJarkeld card) {
        super(card);
    }

    @Override
    public LostOrderOfJarkeld copy() {
        return new LostOrderOfJarkeld(this);
    }
}

enum LostOrderOfJarkeldValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (game.getPermanent(sourceAbility.getSourceId()) == null) {
            return 1;
        }
        Object obj = game.getState().getValue(sourceAbility.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
        if (!(obj instanceof UUID)) {
            return 1;
        }
        return 1 + game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, (UUID) obj, game).size();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "1 plus the number of creatures the chosen player controls.";
    }
}
