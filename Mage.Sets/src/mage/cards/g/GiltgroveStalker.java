package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GiltgroveStalker extends CardImpl {

    public GiltgroveStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Giltgrove Stalker can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private GiltgroveStalker(final GiltgroveStalker card) {
        super(card);
    }

    @Override
    public GiltgroveStalker copy() {
        return new GiltgroveStalker(this);
    }
}
