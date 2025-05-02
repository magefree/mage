package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.CrewedVehicleWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuxuriousLocomotive extends CardImpl {

    public LuxuriousLocomotive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Whenever Luxurious Locomotive attacks, create a Treasure token for each creature that crewed it this turn.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new TreasureToken(), LuxuriousLocomotiveValue.instance
        )), new CrewedVehicleWatcher());

        // Crew 1. Activate only once each turn.
        ActivatedAbility ability = new CrewAbility(1);
        ability.setMaxActivationsPerTurn(1);
        this.addAbility(ability);
    }

    private LuxuriousLocomotive(final LuxuriousLocomotive card) {
        super(card);
    }

    @Override
    public LuxuriousLocomotive copy() {
        return new LuxuriousLocomotive(this);
    }
}

enum LuxuriousLocomotiveValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CrewedVehicleWatcher.getCrewCount(sourceAbility.getSourcePermanentOrLKI(game), game);
    }

    @Override
    public LuxuriousLocomotiveValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature that crewed it this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
