package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class HarvestMage extends CardImpl {

    public HarvestMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {T}, Discard a card: Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new HarvestMageReplacementEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private HarvestMage(final HarvestMage card) {
        super(card);
    }

    @Override
    public HarvestMage copy() {
        return new HarvestMage(this);
    }
}

class HarvestMageReplacementEffect extends ReplacementEffectImpl {

    HarvestMageReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount";
    }

    private HarvestMageReplacementEffect(final HarvestMageReplacementEffect effect) {
        super(effect);
    }

    @Override
    public HarvestMageReplacementEffect copy() {
        return new HarvestMageReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        if (game != null && game.inCheckPlayableState()) {
            mana.setToMana(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
            return false;
        }
        new AddManaOfAnyColorEffect().apply(game, source);
        mana.setToMana(new Mana(0, 0, 0, 0, 0, 0, 0, 0));
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game) && permanent.isControlledBy(source.getControllerId());
    }
}
