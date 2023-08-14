package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class GossamerPhantasm extends CardImpl {

    public GossamerPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Gossamer Phantasm becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private GossamerPhantasm(final GossamerPhantasm card) {
        super(card);
    }

    @Override
    public GossamerPhantasm copy() {
        return new GossamerPhantasm(this);
    }
}
