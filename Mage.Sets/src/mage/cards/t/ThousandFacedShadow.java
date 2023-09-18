package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThousandFacedShadow extends CardImpl {

    public ThousandFacedShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ninjutsu {2}{U}{U}
        this.addAbility(new NinjutsuAbility("{2}{U}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thousand-Faced Shadow enters the battlefield from your hand, if it's attacking, create a token that's a copy of another target attacking creature. The token enters the battlefield tapped and attacking.
        this.addAbility(new ThousandFacedShadowTriggeredAbility());
    }

    private ThousandFacedShadow(final ThousandFacedShadow card) {
        super(card);
    }

    @Override
    public ThousandFacedShadow copy() {
        return new ThousandFacedShadow(this);
    }
}

class ThousandFacedShadowTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("another attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    ThousandFacedShadowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, true
        ));
        this.addTarget(new TargetPermanent(filter));
    }

    private ThousandFacedShadowTriggeredAbility(final ThousandFacedShadowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThousandFacedShadowTriggeredAbility copy() {
        return new ThousandFacedShadowTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && ((EntersTheBattlefieldEvent) event).getFromZone() == Zone.HAND
                && permanent.getId().equals(getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.isAttacking();
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield from your hand, if it's attacking, " +
                "create a token that's a copy of another target attacking creature. " +
                "The token enters the battlefield tapped and attacking.";
    }
}
