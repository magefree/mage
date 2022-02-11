package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LambholtElder extends CardImpl {

    public LambholtElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.s.SilverpeltWerewolf.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Elder.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private LambholtElder(final LambholtElder card) {
        super(card);
    }

    @Override
    public LambholtElder copy() {
        return new LambholtElder(this);
    }
}