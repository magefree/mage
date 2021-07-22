package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Eyetwitch extends CardImpl {

    public Eyetwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.EYE);
        this.subtype.add(SubType.BAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Eyetwitch dies, learn.
        this.addAbility(new DiesSourceTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));
    }

    private Eyetwitch(final Eyetwitch card) {
        super(card);
    }

    @Override
    public Eyetwitch copy() {
        return new Eyetwitch(this);
    }
}
