package mage.cards.i;

import mage.ConditionalMana;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J (based on jeffwadsworth)
 */
public final class IceCauldron extends CardImpl {

    public IceCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {X}, {T}: Put a charge counter on Ice Cauldron and exile a nonland card from your hand. You may cast that card for as long as it remains exiled. Note the type and amount of mana spent to pay this activation cost. Activate this ability only if there are no charge counters on Ice Cauldron.
        ConditionalActivatedAbility ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), new ManaCostsImpl("{X}"), new SourceHasCounterCondition(CounterType.CHARGE, 0, 0));
        ability.addEffect(new IceCauldronExileEffect());
        ability.addEffect(new IceCauldronNoteManaEffect());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove a charge counter from Ice Cauldron: Add Ice Cauldron's last noted type and amount of mana. Spend this mana only to cast the last card exiled with Ice Cauldron.
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, new IceCauldronAddManaEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability2);
    }

    private IceCauldron(final IceCauldron card) {
        super(card);
    }

    @Override
    public IceCauldron copy() {
        return new IceCauldron(this);
    }
}

class IceCauldronExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public IceCauldronExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "and exile a nonland card from your hand. You may cast that card for as long as it remains exiled";
    }

    public IceCauldronExileEffect(final IceCauldronExileEffect effect) {
        super(effect);
    }

    @Override
    public IceCauldronExileEffect copy() {
        return new IceCauldronExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (controller.getHand().isEmpty()) {
                return true;
            }
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card chosenCard = null;
            if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                chosenCard = controller.getHand().get(target.getFirstTarget(), game);
            }
            if (chosenCard != null) {
                controller.moveCardToExileWithInfo(chosenCard, source.getSourceId(), sourcePermanent.getIdName(), source, game, Zone.HAND, true);
                AsThoughEffect effect = new IceCauldronCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(chosenCard.getId(), game));
                game.addEffect(effect, source);
                game.getState().setValue("IceCauldronCard" + source.getSourceId().toString(), new MageObjectReference(chosenCard.getId(), game)); //store the exiled card
                return true;
            }
        }
        return false;
    }
}

class IceCauldronCastFromExileEffect extends AsThoughEffectImpl {

    IceCauldronCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled";
    }

    IceCauldronCastFromExileEffect(final IceCauldronCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IceCauldronCastFromExileEffect copy() {
        return new IceCauldronCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(objectId)
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player player = game.getPlayer(source.getControllerId());
            Card card = game.getCard(objectId);
            return player != null
                    && card != null;
        }
        return false;
    }
}

class IceCauldronNoteManaEffect extends OneShotEffect {

    private String manaUsedString;

    public IceCauldronNoteManaEffect() {
        super(Outcome.Benefit);
        this.staticText = "Note the type and amount of mana spent to pay this activation cost";
    }

    public IceCauldronNoteManaEffect(final IceCauldronNoteManaEffect effect) {
        super(effect);
        manaUsedString = effect.manaUsedString;
    }

    @Override
    public IceCauldronNoteManaEffect copy() {
        return new IceCauldronNoteManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent iceCauldron = game.getPermanent(source.getSourceId());
        if (controller != null && iceCauldron != null) {
            game.getState().setValue("IceCauldronMana" + source.getSourceId().toString(), source.getManaCostsToPay().getUsedManaToPay()); //store the mana used to pay
            manaUsedString = source.getManaCostsToPay().getUsedManaToPay().toString();
            iceCauldron.addInfo("MANA USED", CardUtil.addToolTipMarkTags("Mana used last: " + manaUsedString), game);
            return true;
        }
        return false;
    }
}

class IceCauldronAddManaEffect extends ManaEffect {

    private Mana storedMana;
    private MageObjectReference exiledCardMor;

    IceCauldronAddManaEffect() {
        super();
        staticText = "Add {this}'s last noted type and amount of mana. Spend this mana only to cast the last card exiled with {this}";
    }

    IceCauldronAddManaEffect(IceCauldronAddManaEffect effect) {
        super(effect);
        storedMana = effect.storedMana == null ? null : effect.storedMana.copy();
        exiledCardMor = effect.exiledCardMor;
    }

    @Override
    public IceCauldronAddManaEffect copy() {
        return new IceCauldronAddManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent iceCauldron = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (iceCauldron != null && controller != null) {
            storedMana = (Mana) game.getState().getValue("IceCauldronMana" + source.getSourceId().toString());
            exiledCardMor = (MageObjectReference) game.getState().getValue("IceCauldronCard" + source.getSourceId().toString());
            if (storedMana != null) { // should be adding the mana even if exiled card is null
                Card card = exiledCardMor.getCard(game);
                if (card == null) {
                    card = game.getCard(exiledCardMor.getSourceId());
                    if (card != null && !(card.getZoneChangeCounter(game) == exiledCardMor.getZoneChangeCounter() + 1 && game.getState().getZone(card.getId()) == Zone.STACK)) {
                        card = null;
                    }
                }
                if (card != null) {
                    return new IceCauldronConditionalMana(storedMana, card);
                }
            }
        }
        return mana;
    }

}

class IceCauldronConditionalMana extends ConditionalMana {

    public IceCauldronConditionalMana(Mana mana, Card exiledCard) {
        super(mana);
        staticText = "Spend this mana only to cast the last card exiled with {this}";
        addCondition(new IceCauldronManaCondition(exiledCard));
    }
}

class IceCauldronManaCondition implements Condition {

    private final Card exiledCard;

    public IceCauldronManaCondition(Card exiledCard) {
        this.exiledCard = exiledCard;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            return card != null && card.equals(exiledCard);
        }
        return false;
    }
}
