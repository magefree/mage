package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WatcherOfTheWayside extends CardImpl {

    public WatcherOfTheWayside(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, target player mills two cards. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private WatcherOfTheWayside(final WatcherOfTheWayside card) {
        super(card);
    }

    @Override
    public WatcherOfTheWayside copy() {
        return new WatcherOfTheWayside(this);
    }
}
