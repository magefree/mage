package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TymorasInvoker extends CardImpl {

    public TymorasInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Sleight of Hand — {8}: Draw two cards.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new GenericManaCost(8)
        ).withFlavorWord("Sleight of Hand"));
    }

    private TymorasInvoker(final TymorasInvoker card) {
        super(card);
    }

    @Override
    public TymorasInvoker copy() {
        return new TymorasInvoker(this);
    }
}
