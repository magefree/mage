package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavernStomper extends CardImpl {

    public CavernStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Cavern Stomper enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {3}{G}: Cavern Stomper can't be blocked by creatures with power 2 or less this turn.
        this.addAbility(new SimpleActivatedAbility(new CantBeBlockedByCreaturesSourceEffect(
                DauntAbility.getFilter(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{3}{G}")));
    }

    private CavernStomper(final CavernStomper card) {
        super(card);
    }

    @Override
    public CavernStomper copy() {
        return new CavernStomper(this);
    }
}
