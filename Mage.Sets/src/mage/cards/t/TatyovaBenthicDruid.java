package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author L_J
 */
public final class TatyovaBenthicDruid extends CardImpl {

    public TatyovaBenthicDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK, SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield under your control, you gain 1 life and draw a card.
        Ability ability = new LandfallAbility(new GainLifeEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        this.addAbility(ability);
    }

    private TatyovaBenthicDruid(final TatyovaBenthicDruid card) {
        super(card);
    }

    @Override
    public TatyovaBenthicDruid copy() {
        return new TatyovaBenthicDruid(this);
    }
}
