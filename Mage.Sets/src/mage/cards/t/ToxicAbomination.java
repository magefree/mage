package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToxicAbomination extends CardImpl {

    public ToxicAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Toxic Abomination enters the battlefield, you lose 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(2)));
    }

    private ToxicAbomination(final ToxicAbomination card) {
        super(card);
    }

    @Override
    public ToxicAbomination copy() {
        return new ToxicAbomination(this);
    }
}
