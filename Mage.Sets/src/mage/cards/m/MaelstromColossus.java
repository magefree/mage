package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaelstromColossus extends CardImpl {

    public MaelstromColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private MaelstromColossus(final MaelstromColossus card) {
        super(card);
    }

    @Override
    public MaelstromColossus copy() {
        return new MaelstromColossus(this);
    }
}
