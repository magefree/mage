package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.SelectCopiableCharacteristicsSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PrimalPlasma extends CardImpl {

    public PrimalPlasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender.
        this.addAbility(new AsEntersBattlefieldAbility(new SelectCopiableCharacteristicsSourceEffect(
                new PrimalPlasma33Token(), new PrimalPlasma22Token(), new PrimalPlasma16Token()
        )));
    }

    private PrimalPlasma(final PrimalPlasma card) {
        super(card);
    }

    @Override
    public PrimalPlasma copy() {
        return new PrimalPlasma(this);
    }

}

class PrimalPlasma33Token extends TokenImpl {

    PrimalPlasma33Token() {
        super("", "3/3 creature");
        cardType.add(CardType.CREATURE);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private PrimalPlasma33Token(final PrimalPlasma33Token token) {
        super(token);
    }

    public PrimalPlasma33Token copy() {
        return new PrimalPlasma33Token(this);
    }
}

class PrimalPlasma22Token extends TokenImpl {

    PrimalPlasma22Token() {
        super("", "2/2 creature with flying");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private PrimalPlasma22Token(final PrimalPlasma22Token token) {
        super(token);
    }

    public PrimalPlasma22Token copy() {
        return new PrimalPlasma22Token(this);
    }
}

class PrimalPlasma16Token extends TokenImpl {

    PrimalPlasma16Token() {
        super("", "1/6 creature with defender");
        cardType.add(CardType.CREATURE);
        power = new MageInt(1);
        toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
    }

    private PrimalPlasma16Token(final PrimalPlasma16Token token) {
        super(token);
    }

    public PrimalPlasma16Token copy() {
        return new PrimalPlasma16Token(this);
    }
}
