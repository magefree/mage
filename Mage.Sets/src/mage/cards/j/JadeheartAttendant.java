package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadeheartAttendant extends CardImpl {

    public JadeheartAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.nightCard = true;
        this.color.setGreen(true);

        // When Jadeheart Attendant enters the battlefield, you gain life equal to the mana value of the exiled card used to craft it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(JadeheartAttendantValue.instance)
                .setText("you gain life equal to the mana value of the exiled card used to craft it")));
    }

    private JadeheartAttendant(final JadeheartAttendant card) {
        super(card);
    }

    @Override
    public JadeheartAttendant copy() {
        return new JadeheartAttendant(this);
    }
}

enum JadeheartAttendantValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, sourceAbility, -2));
        return exileZone != null
                ? exileZone
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum()
                : 0;
    }

    @Override
    public JadeheartAttendantValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
