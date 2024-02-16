package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombatThresher extends CardImpl {

    public CombatThresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prototype {2}{W} -- 1/1
        this.addAbility(new PrototypeAbility(this, "{2}{W}", 1, 1));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Combat Thresher enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private CombatThresher(final CombatThresher card) {
        super(card);
    }

    @Override
    public CombatThresher copy() {
        return new CombatThresher(this);
    }
}
