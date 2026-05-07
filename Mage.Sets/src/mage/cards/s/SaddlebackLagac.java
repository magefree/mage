package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SaddlebackLagac extends CardImpl {

    public SaddlebackLagac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Saddleback Lagac enters the battlefield, support 2.
        this.addAbility(new SupportAbility(this, 2));
    }

    private SaddlebackLagac(final SaddlebackLagac card) {
        super(card);
    }

    @Override
    public SaddlebackLagac copy() {
        return new SaddlebackLagac(this);
    }
}
