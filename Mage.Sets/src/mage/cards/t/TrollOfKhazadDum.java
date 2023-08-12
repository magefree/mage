package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrollOfKhazadDum extends CardImpl {

    public TrollOfKhazadDum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Troll of Khazad-dum can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByOneEffect(3)));

        // Swampcycling {1}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private TrollOfKhazadDum(final TrollOfKhazadDum card) {
        super(card);
    }

    @Override
    public TrollOfKhazadDum copy() {
        return new TrollOfKhazadDum(this);
    }
}
