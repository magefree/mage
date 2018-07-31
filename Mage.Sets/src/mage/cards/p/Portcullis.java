package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class Portcullis extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    public Portcullis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature.
        String rule = "Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature";
        TriggeredAbility ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new PortcullisExileEffect(), filter, false, SetTargetPointer.PERMANENT, rule);
        MoreThanXCreaturesOnBFCondition condition = new MoreThanXCreaturesOnBFCondition(2);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition, rule));

        // Return that card to the battlefield under its owner's control when Portcullis leaves the battlefield.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    public Portcullis(final Portcullis card) {
        super(card);
    }

    @Override
    public Portcullis copy() {
        return new Portcullis(this);
    }
}

class MoreThanXCreaturesOnBFCondition implements Condition {

    protected final int value;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures on field");

    public MoreThanXCreaturesOnBFCondition(int value) {
        this.value = value;
    }

    @Override
    public final boolean apply(Game game, Ability source) {
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter);
        int count = amount.calculate(game, source, null);
        return count > value;
    }
}

class PortcullisExileEffect extends OneShotEffect {

    public PortcullisExileEffect() {
        super(Outcome.Neutral);
        this.staticText = "Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature.";
    }

    public PortcullisExileEffect(final PortcullisExileEffect effect) {
        super(effect);
    }

    @Override
    public PortcullisExileEffect copy() {
        return new PortcullisExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (permanent != null && creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            Zone currentZone = game.getState().getZone(creature.getId());
            if (currentZone == Zone.BATTLEFIELD) {
                controller.moveCardsToExile(creature, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
                return true;
            }
        }
        return false;
    }
}
