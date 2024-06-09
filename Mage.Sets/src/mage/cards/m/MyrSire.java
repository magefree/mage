package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianMyrToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MyrSire extends CardImpl {

    public MyrSire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PhyrexianMyrToken())));
    }

    private MyrSire(final MyrSire card) {
        super(card);
    }

    @Override
    public MyrSire copy() {
        return new MyrSire(this);
    }
}
