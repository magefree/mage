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
public final class PrimalClay extends CardImpl {

    public PrimalClay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        this.addAbility(new AsEntersBattlefieldAbility(new SelectCopiableCharacteristicsSourceEffect(
                new PrimalClay33Token(), new PrimalClay22Token(), new PrimalClay16Token()
        )));    
    }

    private PrimalClay(final PrimalClay card) {
        super(card);
    }

    @Override
    public PrimalClay copy() {
        return new PrimalClay(this);
    }

}

class PrimalClay33Token extends TokenImpl {

    PrimalClay33Token() {
        super("", "3/3 artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private PrimalClay33Token(final PrimalClay33Token token) {
        super(token);
    }

    public PrimalClay33Token copy() {
        return new PrimalClay33Token(this);
    }
}

class PrimalClay22Token extends TokenImpl {

    PrimalClay22Token() {
        super("", "2/2 artifact creature with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private PrimalClay22Token(final PrimalClay22Token token) {
        super(token);
    }

    public PrimalClay22Token copy() {
        return new PrimalClay22Token(this);
    }
}

class PrimalClay16Token extends TokenImpl {

    PrimalClay16Token() {
        super("", "1/6 Wall artifact creature with defender");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(1);
        toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
    }

    private PrimalClay16Token(final PrimalClay16Token token) {
        super(token);
    }

    public PrimalClay16Token copy() {
        return new PrimalClay16Token(this);
    }
}
