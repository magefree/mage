package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MatoyaArchonElder extends CardImpl {

    public MatoyaArchonElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you scry or surveil, draw a card.
        this.addAbility(new ScryOrSurveilTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private MatoyaArchonElder(final MatoyaArchonElder card) {
        super(card);
    }

    @Override
    public MatoyaArchonElder copy() {
        return new MatoyaArchonElder(this);
    }
}
