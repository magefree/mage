
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
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
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HarvestMageReplacementEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }

    public HarvestMage(final HarvestMage card) {
        super(card);
    }

    @Override
    public HarvestMage copy() {
        return new HarvestMage(this);
    }
}

class HarvestMageReplacementEffect extends ReplacementEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    HarvestMageReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount";
    }

    HarvestMageReplacementEffect(final HarvestMageReplacementEffect effect) {
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
        new AddManaOfAnyColorEffect().apply(game,source);
        mana.setToMana(new Mana(0,0,0,0,0,0,0,0));
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (mageObject != null && mageObject.isLand()) {
            Permanent land = game.getPermanent(event.getSourceId());
            return land != null && filter.match(land, game);
        }
        return false;
    }
}
