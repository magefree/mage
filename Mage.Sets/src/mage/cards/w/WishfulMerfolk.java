package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class WishfulMerfolk extends CardImpl {

    public WishfulMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}{U}: Wishful Merfolk loses defender and becomes a Human until end of turn.
        //this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WishfulMerfolkEffect(), new ManaCostsImpl("{1}{U}")));
       this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new WishfulMerfolkToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{1}{U}")));
    }

    public WishfulMerfolk(final WishfulMerfolk card) {
        super(card);
    }

    @Override
    public WishfulMerfolk copy() {
        return new WishfulMerfolk(this);
    }
}

class WishfulMerfolkToken extends TokenImpl {

    public WishfulMerfolkToken() {
        super("Wishful Merfolk", "");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    public WishfulMerfolkToken(final WishfulMerfolkToken token) {
        super(token);
    }

    public WishfulMerfolkToken copy() {
        return new WishfulMerfolkToken(this);
    }
}
