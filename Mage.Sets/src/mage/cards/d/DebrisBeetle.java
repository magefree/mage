package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DebrisBeetle extends CardImpl {

    public DebrisBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this Vehicle enters, each opponent loses 3 life and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(3));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DebrisBeetle(final DebrisBeetle card) {
        super(card);
    }

    @Override
    public DebrisBeetle copy() {
        return new DebrisBeetle(this);
    }
}
