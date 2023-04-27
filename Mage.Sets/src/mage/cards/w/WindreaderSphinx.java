
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class WindreaderSphinx extends CardImpl {

    public WindreaderSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a creature with flying attacks, you may draw a card.
        this.addAbility(new WindreaderSphinxTriggeredAbility());
    }

    private WindreaderSphinx(final WindreaderSphinx card) {
        super(card);
    }

    @Override
    public WindreaderSphinx copy() {
        return new WindreaderSphinx(this);
    }
}

class WindreaderSphinxTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WindreaderSphinxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        setTriggerPhrase("Whenever a creature with flying attacks, ");
    }

    public WindreaderSphinxTriggeredAbility(final WindreaderSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        return attacker != null && filter.match(attacker, game);
    }

    @Override
    public WindreaderSphinxTriggeredAbility copy() {
        return new WindreaderSphinxTriggeredAbility(this);
    }
}
