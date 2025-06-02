package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanguardSeraph extends CardImpl {

    public VanguardSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life for the first time each turn, surveil 1.
        this.addAbility(new GainLifeFirstTimeTriggeredAbility(new SurveilEffect(1)));
    }

    private VanguardSeraph(final VanguardSeraph card) {
        super(card);
    }

    @Override
    public VanguardSeraph copy() {
        return new VanguardSeraph(this);
    }
}
