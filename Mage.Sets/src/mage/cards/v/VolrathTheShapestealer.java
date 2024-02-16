package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolrathTheShapestealer extends CardImpl {

    static final FilterPermanent filter = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public VolrathTheShapestealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, put a -1/-1 counter on up to one target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance(), Outcome.Detriment), TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {1}: Until your next turn, Volrath, the Shapestealer becomes a copy of target creature with a counter on it, except it's 7/5 and it has this ability.
        ability = new SimpleActivatedAbility(new VolrathTheShapestealerEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VolrathTheShapestealer(final VolrathTheShapestealer card) {
        super(card);
    }

    @Override
    public VolrathTheShapestealer copy() {
        return new VolrathTheShapestealer(this);
    }
}

class VolrathTheShapestealerEffect extends OneShotEffect {

    VolrathTheShapestealerEffect() {
        super(Outcome.Copy);
        staticText = "Until your next turn, {this} becomes a copy of target creature with a counter on it, "
                + "except it's 7/5 and it has this ability.";
    }

    private VolrathTheShapestealerEffect(final VolrathTheShapestealerEffect effect) {
        super(effect);
    }

    @Override
    public VolrathTheShapestealerEffect copy() {
        return new VolrathTheShapestealerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent volrathTheShapestealer = game.getPermanent(source.getSourceId());
        Permanent newBluePrint = null;
        if (controller == null
                || volrathTheShapestealer == null) {
            return false;
        }
        Card copyFromCard = game.getPermanent(source.getFirstTarget());
        if (copyFromCard == null) {
            return true;
        }
        newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyApplier applier = new VolrathTheShapestealerCopyApplier();
        applier.apply(game, newBluePrint, source, volrathTheShapestealer.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.UntilYourNextTurn, newBluePrint, volrathTheShapestealer.getId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return true;
    }
}

class VolrathTheShapestealerCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        Ability ability = new SimpleActivatedAbility(new VolrathTheShapestealerEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(VolrathTheShapestealer.filter));
        blueprint.getAbilities().add(ability);
        blueprint.removePTCDA();
        blueprint.getPower().setModifiedBaseValue(7);
        blueprint.getToughness().setModifiedBaseValue(5);
        return true;
    }
}
