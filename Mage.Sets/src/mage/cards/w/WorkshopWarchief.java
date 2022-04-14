package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RhinoWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorkshopWarchief extends CardImpl {

    public WorkshopWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Workshop Warchief enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // When Workshop Warchief dies, create a 4/4 green Rhino Warrior creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new RhinoWarriorToken())));

        // Blitz {4}{G}{G}
        this.addAbility(new BlitzAbility("{4}{G}{G}"));
    }

    private WorkshopWarchief(final WorkshopWarchief card) {
        super(card);
    }

    @Override
    public WorkshopWarchief copy() {
        return new WorkshopWarchief(this);
    }
}
