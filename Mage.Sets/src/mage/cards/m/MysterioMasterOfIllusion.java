package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IllusionVillainToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysterioMasterOfIllusion extends CardImpl {

    public MysterioMasterOfIllusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Mysterio enters, create a 3/3 blue Illusion Villain creature token for each nontoken Villain you control. Exile those tokens when Mysterio leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MysterioMasterOfIllusionEffect()));
    }

    private MysterioMasterOfIllusion(final MysterioMasterOfIllusion card) {
        super(card);
    }

    @Override
    public MysterioMasterOfIllusion copy() {
        return new MysterioMasterOfIllusion(this);
    }
}

class MysterioMasterOfIllusionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN);

    static {
        filter.add(TokenPredicate.FALSE);
    }

    MysterioMasterOfIllusionEffect() {
        super(Outcome.Benefit);
        staticText = "create a 3/3 blue Illusion Villain creature token for each nontoken Villain you control. " +
                "Exile those tokens when {this} leaves the battlefield";
    }

    private MysterioMasterOfIllusionEffect(final MysterioMasterOfIllusionEffect effect) {
        super(effect);
    }

    @Override
    public MysterioMasterOfIllusionEffect copy() {
        return new MysterioMasterOfIllusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new IllusionVillainToken();
        token.putOntoBattlefield(game.getBattlefield().count(filter, source.getControllerId(), source, game), game, source);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            game.addDelayedTriggeredAbility(new MysterioMasterOfIllusionTriggeredAbility(token, game), source);
        }
        return true;
    }
}

class MysterioMasterOfIllusionTriggeredAbility extends DelayedTriggeredAbility {

    MysterioMasterOfIllusionTriggeredAbility(Token token, Game game) {
        super(new ExileTargetEffect().setTargetPointer(new FixedTargets(token, game)), Duration.Custom, true, false);
        this.setLeavesTheBattlefieldTrigger(true);
    }

    private MysterioMasterOfIllusionTriggeredAbility(final MysterioMasterOfIllusionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MysterioMasterOfIllusionTriggeredAbility copy() {
        return new MysterioMasterOfIllusionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Zone.BATTLEFIELD.match(((ZoneChangeEvent) event).getFromZone())
                && event.getSourceId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Exile those tokens when {this} leaves the battlefield.";
    }
}
