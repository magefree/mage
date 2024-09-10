package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class JoriEnRuinDiver extends CardImpl {

    public JoriEnRuinDiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, draw a card.
        this.addAbility(new CastSecondSpellTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private JoriEnRuinDiver(final JoriEnRuinDiver card) {
        super(card);
    }

    @Override
    public JoriEnRuinDiver copy() {
        return new JoriEnRuinDiver(this);
    }
}
