package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerumVisionary extends CardImpl {

    public SerumVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Serum Visionary enters the battlefield, draw a card, then scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ScryEffect(2).concatBy(", then"));
        this.addAbility(ability);
    }

    private SerumVisionary(final SerumVisionary card) {
        super(card);
    }

    @Override
    public SerumVisionary copy() {
        return new SerumVisionary(this);
    }
}
