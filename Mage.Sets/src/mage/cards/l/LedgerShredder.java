package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LedgerShredder extends CardImpl {

    public LedgerShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player casts their second spell each turn, Ledger Shredder connives.
        this.addAbility(new CastSecondSpellTriggeredAbility(new ConniveSourceEffect(), TargetController.ANY));
    }

    private LedgerShredder(final LedgerShredder card) {
        super(card);
    }

    @Override
    public LedgerShredder copy() {
        return new LedgerShredder(this);
    }
}
