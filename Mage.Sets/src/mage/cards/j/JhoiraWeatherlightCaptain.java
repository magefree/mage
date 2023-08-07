package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;

import java.util.UUID;

public final class JhoiraWeatherlightCaptain extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();

    public JhoiraWeatherlightCaptain(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        subtype.add(SubType.HUMAN, SubType.ARTIFICER);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Whenever you cast a historic spell, draw a card. <i>(Artifacts, legendaries, and Sagas are historic.)</i>
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("draw a card. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                filter, false
        ));

    }

    public JhoiraWeatherlightCaptain(final JhoiraWeatherlightCaptain jhoiraWeatherlightCaptain) {
        super(jhoiraWeatherlightCaptain);
    }

    @Override
    public JhoiraWeatherlightCaptain copy() {
        return new JhoiraWeatherlightCaptain(this);
    }
}
