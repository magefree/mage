package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class NightveilFaerie extends CardImpl {

    public NightveilFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Nightveil Faerie attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1), false));
    }

    public NightveilFaerie(final NightveilFaerie card) {
        super(card);
    }

    @Override
    public NightveilFaerie copy() {
        return new NightveilFaerie(this);
    }
}
