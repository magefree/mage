package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TigerSharkAbyssalHunter extends CardImpl {

    public TigerSharkAbyssalHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Tiger Shark enters or attacks, he connives.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ConniveSourceEffect()));

        // {4}{U/B}: Tiger Shark can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
            new CantBeBlockedSourceEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{4}{U/B}")
        ));
    }

    private TigerSharkAbyssalHunter(final TigerSharkAbyssalHunter card) {
        super(card);
    }

    @Override
    public TigerSharkAbyssalHunter copy() {
        return new TigerSharkAbyssalHunter(this);
    }
}
