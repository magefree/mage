package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GeralfsMessenger extends CardImpl {

    public GeralfsMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Geralf's Messenger enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Geralf's Messenger enters the battlefield, target opponent loses 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Undying
        this.addAbility(new UndyingAbility());
    }

    private GeralfsMessenger(final GeralfsMessenger card) {
        super(card);
    }

    @Override
    public GeralfsMessenger copy() {
        return new GeralfsMessenger(this);
    }
}
