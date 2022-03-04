package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class ConvictedKiller extends CardImpl {

    public ConvictedKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.b.BrandedHowler.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Convicted Killer.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private ConvictedKiller(final ConvictedKiller card) {
        super(card);
    }

    @Override
    public ConvictedKiller copy() {
        return new ConvictedKiller(this);
    }
}
