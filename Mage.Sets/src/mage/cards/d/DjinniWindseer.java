package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DjinniWindseer extends CardImpl {

    public DjinniWindseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Djinni Windseeker enters the battlefield, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // 1-9 | Scry 1.
        effect.addTableEntry(1, 9, new ScryEffect(1, false));

        // 10-19 | Scry 2.
        effect.addTableEntry(10, 19, new ScryEffect(2, false));

        // 20 | Scry 3.
        effect.addTableEntry(20, 20, new ScryEffect(3, false));
    }

    private DjinniWindseer(final DjinniWindseer card) {
        super(card);
    }

    @Override
    public DjinniWindseer copy() {
        return new DjinniWindseer(this);
    }
}
