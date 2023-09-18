package mage.cards.r;

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
public final class RecklessWaif extends CardImpl {

    public RecklessWaif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.m.MercilessPredator.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Reckless Waif.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private RecklessWaif(final RecklessWaif card) {
        super(card);
    }

    @Override
    public RecklessWaif copy() {
        return new RecklessWaif(this);
    }
}
