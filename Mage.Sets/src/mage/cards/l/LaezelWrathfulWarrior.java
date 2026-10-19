package mage.cards.l;

import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

public final class LaezelWrathfulWarrior extends CardImpl {

    public LaezelWrathfulWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.extraDeckCard = true;
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new LaezelSpecializedTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2)));
    }

    private LaezelWrathfulWarrior(final LaezelWrathfulWarrior card) {
        super(card);
    }

    @Override
    public LaezelWrathfulWarrior copy() {
        return new LaezelWrathfulWarrior(this);
    }
}
