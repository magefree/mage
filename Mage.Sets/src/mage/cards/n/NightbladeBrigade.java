package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightbladeBrigade extends CardImpl {

    public NightbladeBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));
    }

    private NightbladeBrigade(final NightbladeBrigade card) {
        super(card);
    }

    @Override
    public NightbladeBrigade copy() {
        return new NightbladeBrigade(this);
    }
}
