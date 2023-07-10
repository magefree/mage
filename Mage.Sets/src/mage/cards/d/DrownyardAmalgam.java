package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrownyardAmalgam extends CardImpl {

    public DrownyardAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // When Drownyard Amalgam enters the battlefield, target player mills three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(3));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {2}{U}: Drownyard Amalgam can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}")
        ));
    }

    private DrownyardAmalgam(final DrownyardAmalgam card) {
        super(card);
    }

    @Override
    public DrownyardAmalgam copy() {
        return new DrownyardAmalgam(this);
    }
}
