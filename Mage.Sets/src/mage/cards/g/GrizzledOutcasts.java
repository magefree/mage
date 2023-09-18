package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GrizzledOutcasts extends CardImpl {

    public GrizzledOutcasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.k.KrallenhordeWantons.class;

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Grizzled Outcasts.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private GrizzledOutcasts(final GrizzledOutcasts card) {
        super(card);
    }

    @Override
    public GrizzledOutcasts copy() {
        return new GrizzledOutcasts(this);
    }
}