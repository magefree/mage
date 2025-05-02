package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColdCaseCracker extends CardImpl {

    public ColdCaseCracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Cold Case Cracker dies, investigate.
        this.addAbility(new DiesSourceTriggeredAbility(new InvestigateEffect()));
    }

    private ColdCaseCracker(final ColdCaseCracker card) {
        super(card);
    }

    @Override
    public ColdCaseCracker copy() {
        return new ColdCaseCracker(this);
    }
}
