package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CipherboundSpirit extends CardImpl {

    public CipherboundSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cipherbound Spirit can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());

        // {3}{U}: Draw two cards, then discard a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(2, 1), new ManaCostsImpl<>("{3}{U}")
        ));
    }

    private CipherboundSpirit(final CipherboundSpirit card) {
        super(card);
    }

    @Override
    public CipherboundSpirit copy() {
        return new CipherboundSpirit(this);
    }
}
