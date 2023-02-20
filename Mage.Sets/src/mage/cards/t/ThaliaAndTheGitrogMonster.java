package mage.cards.t;

import mage.MageInt;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

public class ThaliaAndTheGitrogMonster extends CardImpl {
    public ThaliaAndTheGitrogMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.FROG);
        this.addSubType(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private ThaliaAndTheGitrogMonster(final ThaliaAndTheGitrogMonster card) {
        super(card);
    }

    @Override
    public ThaliaAndTheGitrogMonster copy() {
        return new ThaliaAndTheGitrogMonster(this);
    }
}
