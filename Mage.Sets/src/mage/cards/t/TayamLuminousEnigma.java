package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public final class TayamLuminousEnigma extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("among creatures you control");

    static {
        filter.add(new CounterPredicate(null));
    }

    public TayamLuminousEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other creature you control enters the battlefield with an additional vigilance counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TayamLuminousEnigmaReplacementEffect()));

        // {3}, Remove three counters from among creatures you control: Put the top three cards of your library into your graveyard, then return a permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        PutTopCardOfLibraryIntoGraveControllerEffect millEffect = new PutTopCardOfLibraryIntoGraveControllerEffect(3);
        millEffect.concatBy(".");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, millEffect, new GenericManaCost(3));
        ability.addCost(new RemoveCounterCost(new TargetPermanent(1, 3, filter, true), null, 3));
        TayamLuminousEnigmaEffect effect = new TayamLuminousEnigmaEffect();
        effect.setText(", then return a permanent card with converted mana cost 3 or less from your graveyard to the battlefield");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private TayamLuminousEnigma(final TayamLuminousEnigma card) {
        super(card);
    }

    @Override
    public TayamLuminousEnigma copy() {
        return new TayamLuminousEnigma(this);
    }
}

class TayamLuminousEnigmaEffect extends OneShotEffect {

    private final FilterCard filter;

    TayamLuminousEnigmaEffect() {
        super(Outcome.Benefit);
        this.filter = new FilterPermanentCard("permanent card in your graveyard with converted mana cost 3 or less");
        this.filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    private TayamLuminousEnigmaEffect(TayamLuminousEnigmaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public TayamLuminousEnigmaEffect copy() {
        return new TayamLuminousEnigmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) == 0) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, game)) {
            return false;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
    }
}

class TayamLuminousEnigmaReplacementEffect extends ReplacementEffectImpl {

    TayamLuminousEnigmaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with an additional vigilance counter on it.";
    }

    private TayamLuminousEnigmaReplacementEffect(final TayamLuminousEnigmaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.isCreature()
                && !source.getSourceId().equals(creature.getId())
                && creature.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.VIGILANCE.createInstance(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public TayamLuminousEnigmaReplacementEffect copy() {
        return new TayamLuminousEnigmaReplacementEffect(this);
    }
}