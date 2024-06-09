package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RMSTitanic extends CardImpl {

    public RMSTitanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When RMS Titanic deals combat damage to a player, sacrifice it and create that many Treasure tokens.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new SacrificeSourceEffect().setText("sacrifice it"), false
        );
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), SavedDamageValue.MANY)
                .concatBy("and"));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private RMSTitanic(final RMSTitanic card) {
        super(card);
    }

    @Override
    public RMSTitanic copy() {
        return new RMSTitanic(this);
    }
}