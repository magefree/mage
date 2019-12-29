package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfNight extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another creature");
    private static final FilterCard filter2
            = new FilterCreatureCard("creature card with converted mana cost 3 or less from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CavalierOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Cavalier of Night enters the battlefield, you may sacrifice another creature. When you do, destroy target creature an opponent controls.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CavalierOfNightCreateReflexiveTriggerEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ).setText("you may sacrifice another creature. When you do, destroy target creature an opponent controls.")));

        // When Cavalier of Night dies, return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new DiesTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private CavalierOfNight(final CavalierOfNight card) {
        super(card);
    }

    @Override
    public CavalierOfNight copy() {
        return new CavalierOfNight(this);
    }
}

class CavalierOfNightCreateReflexiveTriggerEffect extends OneShotEffect {

    CavalierOfNightCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private CavalierOfNightCreateReflexiveTriggerEffect(final CavalierOfNightCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public CavalierOfNightCreateReflexiveTriggerEffect copy() {
        return new CavalierOfNightCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new CavalierOfNightReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class CavalierOfNightReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    CavalierOfNightReflexiveTriggeredAbility() {
        super(new DestroyTargetEffect(), Duration.OneUse, true);
        this.addTarget(new TargetOpponentsCreaturePermanent());
    }

    private CavalierOfNightReflexiveTriggeredAbility(final CavalierOfNightReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CavalierOfNightReflexiveTriggeredAbility copy() {
        return new CavalierOfNightReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Destroy target creature an opponent controls";
    }
}
