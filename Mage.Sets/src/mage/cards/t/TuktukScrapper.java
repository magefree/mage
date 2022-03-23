package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TuktukScrapper extends CardImpl {

    public TuktukScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tuktuk Scrapper or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control.
        this.addAbility(new TuktukScrapperTriggeredAbility());
    }

    private TuktukScrapper(final TuktukScrapper card) {
        super(card);
    }

    @Override
    public TuktukScrapper copy() {
        return new TuktukScrapper(this);
    }
}

class TuktukScrapperTriggeredAbility extends TriggeredAbilityImpl {

    public TuktukScrapperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TuktukScrapperEffect(), true);
        this.addTarget(new TargetArtifactPermanent());
    }

    public TuktukScrapperTriggeredAbility(final TuktukScrapperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TuktukScrapperTriggeredAbility copy() {
        return new TuktukScrapperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.getId().equals(this.getSourceId())) {
                return true;
            }
            return permanent.hasSubtype(SubType.ALLY, game)
                    && permanent.isControlledBy(this.getControllerId());
        }
        return false;
    }

    @Override
    public String getRule() {

        // originally returned fullText, user reported that because the trigger text is so lengthy, they cannot click Yes/No buttons
        //String fullText = "Whenever {this} or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, {this} deals damage to that artifact's controller equal to the number of Allies you control.";
        String condensedText = "Whenever {this} or another Ally you enters the battlefield under your control, you may destroy target artifact. If you do, {this} deals damage to that controller equal to the number of Allies you control.";

        return condensedText;
    }
}

class TuktukScrapperEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public TuktukScrapperEffect() {
        super(Outcome.DestroyPermanent);
    }

    public TuktukScrapperEffect(final TuktukScrapperEffect effect) {
        super(effect);
    }

    @Override
    public TuktukScrapperEffect copy() {
        return new TuktukScrapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetArtifact = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetArtifact != null) {
            targetArtifact.destroy(source, game, false);
            Player targetController = game.getPlayer(targetArtifact.getControllerId());
            if (targetController != null && game.getState().getZone(targetArtifact.getId()) == Zone.GRAVEYARD) {
                int alliesControlled = game.getBattlefield().count(filter, source.getControllerId(), source, game);
                if (alliesControlled > 0) {
                    targetController.damage(alliesControlled, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
