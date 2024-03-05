package mage.cards.i;

import java.util.UUID;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author DominionSpy
 */
public final class IllicitMasquerade extends CardImpl {

    public IllicitMasquerade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Illicit Masquerade enters the battlefield, put an impostor counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.IMPOSTOR.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)));

        // Whenever a creature you control with an impostor counter on it dies, exile it. Return up to one other target creature card from your graveyard to the battlefield.
        this.addAbility(new IllicitMasquerageAbility());
    }

    private IllicitMasquerade(final IllicitMasquerade card) {
        super(card);
    }

    @Override
    public IllicitMasquerade copy() {
        return new IllicitMasquerade(this);
    }
}

class IllicitMasquerageAbility extends DiesCreatureTriggeredAbility {

    private static final FilterPermanent filter1 =
            new FilterCreaturePermanent("a creature you control with an impostor counter on it");
    private static final FilterCard filter2 =
            new FilterCreatureCard("other target creature card from your graveyard");

    static {
        filter1.add(TargetController.YOU.getControllerPredicate());
        filter1.add(CounterType.IMPOSTOR.getPredicate());

        filter2.add(IllicitMasqueradePredicate.instance);
    }

    IllicitMasquerageAbility() {
        super(new ExileTargetEffect().setText("exile it"), false, filter1, true);
        this.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }

    private IllicitMasquerageAbility(final IllicitMasquerageAbility ability) {
        super(ability);
    }

    @Override
    public IllicitMasquerageAbility copy() {
        return new IllicitMasquerageAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            // DiesCreatureTriggeredAbility rewrites targets, so need to point the second effect
            // back to the correct target.
            getEffects().stream()
                    .filter(ReturnFromGraveyardToBattlefieldTargetEffect.class::isInstance)
                    .forEach(effect -> effect.setTargetPointer(new FirstTargetPointer()));
            return true;
        }
        return false;
    }
}

enum IllicitMasqueradePredicate implements ObjectSourcePlayerPredicate<MageItem> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        Ability ability = input.getSource();
        if (!(ability instanceof TriggeredAbility)) {
            return true;
        }
        GameEvent event = ((TriggeredAbility) ability).getTriggerEvent();
        return event == null || !input.getObject().getId().equals(event.getTargetId());
    }
}
