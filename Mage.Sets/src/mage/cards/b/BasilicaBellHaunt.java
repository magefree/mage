package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasilicaBellHaunt extends CardImpl {

    public BasilicaBellHaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{B}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Basilica Bell-Haunt enters the battlefield, each opponent discards a card and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        ability.addEffect(new GainLifeEffect(3).setText("and you gain 3 life"));
        this.addAbility(ability);
    }

    private BasilicaBellHaunt(final BasilicaBellHaunt card) {
        super(card);
    }

    @Override
    public BasilicaBellHaunt copy() {
        return new BasilicaBellHaunt(this);
    }
}
