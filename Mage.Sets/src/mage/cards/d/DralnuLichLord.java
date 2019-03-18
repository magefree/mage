
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class DralnuLichLord extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("instant or sorcery card in your graveyard");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DralnuLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If damage would be dealt to Dralnu, Lich Lord, sacrifice that many permanents instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DralnuLichLordReplacementEffect()));
        
        // {tap}: Target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DralnuLichLordFlashbackEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public DralnuLichLord(final DralnuLichLord card) {
        super(card);
    }

    @Override
    public DralnuLichLord copy() {
        return new DralnuLichLord(this);
    }
}

class DralnuLichLordReplacementEffect extends ReplacementEffectImpl {
    DralnuLichLordReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Sacrifice);
        staticText = "If damage would be dealt to {this}, sacrifice that many permanents instead";
    }

    DralnuLichLordReplacementEffect(final DralnuLichLordReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageCreatureEvent damageEvent = (DamageCreatureEvent) event;
        new SacrificeControllerEffect(new FilterPermanent(), damageEvent.getAmount(), "").apply(game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public DralnuLichLordReplacementEffect copy() {
        return new DralnuLichLordReplacementEffect(this);
    }
}

class DralnuLichLordFlashbackEffect extends ContinuousEffectImpl {

    DralnuLichLordFlashbackEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
    }

    DralnuLichLordFlashbackEffect(final DralnuLichLordFlashbackEffect effect) {
        super(effect);
    }

    @Override
    public DralnuLichLordFlashbackEffect copy() {
        return new DralnuLichLordFlashbackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            FlashbackAbility ability;
            if (card.isInstant()) {
                ability = new FlashbackAbility(card.getManaCost(), TimingRule.INSTANT);
            }
            else {
                ability = new FlashbackAbility(card.getManaCost(), TimingRule.SORCERY);
            }
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
            return true;
        }
        return false;
    }
}
