package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SurveilTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CopyCatchers extends CardImpl {

    public CopyCatchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you surveil, you may pay {1}{U}. If you do, create a token that’s a copy of Copy Catchers.
        this.addAbility(new SurveilTriggeredAbility(new DoIfCostPaid(
                new CreateTokenCopySourceEffect(), new ManaCostsImpl<>("{1}{U}")
        )));
    }

    private CopyCatchers(final CopyCatchers card) {
        super(card);
    }

    @Override
    public CopyCatchers copy() {
        return new CopyCatchers(this);
    }
}
