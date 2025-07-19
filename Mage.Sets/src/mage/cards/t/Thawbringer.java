package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Thawbringer extends CardImpl {

    public Thawbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When this creature enters or dies, surveil 1.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new SurveilEffect(1), false));
    }

    private Thawbringer(final Thawbringer card) {
        super(card);
    }

    @Override
    public Thawbringer copy() {
        return new Thawbringer(this);
    }
}
