
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class AkoumFirebird extends CardImpl {

    public AkoumFirebird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Akoum Firebird attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, you may pay {4}{R}{R}.
        // If you do, return Akoum Firebird from your graveyard to the battlefield.
        this.addAbility(new AkoumFirebirdLandfallAbility(new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false), new ManaCostsImpl<>("{4}{R}{R}")), false));
    }

    private AkoumFirebird(final AkoumFirebird card) {
        super(card);
    }

    @Override
    public AkoumFirebird copy() {
        return new AkoumFirebird(this);
    }
}

class AkoumFirebirdLandfallAbility extends TriggeredAbilityImpl {

    public AkoumFirebirdLandfallAbility(Effect effect, boolean optional) {
        this(Zone.GRAVEYARD, effect, optional);
    }

    public AkoumFirebirdLandfallAbility (Zone zone, Effect effect, boolean optional ) {
        super(zone, effect, optional);
    }

    public AkoumFirebirdLandfallAbility(final AkoumFirebirdLandfallAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isLand(game) && permanent.isControlledBy(this.controllerId);
    }

    @Override
    public String getTriggerPhrase() {
        return "<i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, " ;
    }

    @Override
    public AkoumFirebirdLandfallAbility copy() {
        return new AkoumFirebirdLandfallAbility(this);
    }
}