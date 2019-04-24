package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloomHulk extends CardImpl {

    public BloomHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Bloom Hulk enters the battlefield, proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProliferateEffect()));
    }

    private BloomHulk(final BloomHulk card) {
        super(card);
    }

    @Override
    public BloomHulk copy() {
        return new BloomHulk(this);
    }
}
