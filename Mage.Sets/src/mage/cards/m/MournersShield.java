package mage.cards.m;

import java.util.UUID;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.SharesColorPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author noahg
 */
public final class MournersShield extends CardImpl {

    public MournersShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // Imprint - When Mourner's Shield enters the battlefield, you may exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MournersShieldImprintEffect(), true);
        ability.addTarget(new TargetCardInGraveyard());
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // {2}, {tap}: Prevent all damage that would be dealt this turn by a source of your choice that shares a color with the exiled card.
        Ability preventAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MournersShieldEffect(), new GenericManaCost(2));
        preventAbility.addCost(new TapSourceCost());
        this.addAbility(preventAbility);
    }

    private MournersShield(final MournersShield card) {
        super(card);
    }

    @Override
    public MournersShield copy() {
        return new MournersShield(this);
    }
}

class MournersShieldImprintEffect extends OneShotEffect {

    MournersShieldImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile target card from a graveyard";
    }

    MournersShieldImprintEffect(final MournersShieldImprintEffect effect) {
        super(effect);
    }

    @Override
    public MournersShieldImprintEffect copy() {
        return new MournersShieldImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), source.getSourceObject(game).getIdName());
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    sourcePermanent.imprint(this.getTargetPointer().getFirst(game, source), game);
                }
            }
            return true;
        }
        return false;
    }
}

class MournersShieldEffect extends PreventionEffectImpl {

    private TargetSource target;
    private MageObjectReference mageObjectReference;
    private boolean noneExiled;

    public MournersShieldEffect() {
        super(Duration.EndOfTurn);
        noneExiled = false;
        this.staticText = "Prevent all damage that would be dealt this turn by a source of your choice that shares a color with the exiled card.";
    }

    public MournersShieldEffect(final MournersShieldEffect effect) {
        super(effect);
        if (effect.target != null) {
            this.target = effect.target.copy();
        } else {
            this.target = null;
        }
        this.mageObjectReference = effect.mageObjectReference;
        this.noneExiled = effect.noneExiled;
    }

    @Override
    public MournersShieldEffect copy() {
        return new MournersShieldEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        ObjectColor colorsAmongImprinted = new ObjectColor();
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source.getSourceId()));
        if (sourceObject == null || sourceObject.getImprinted() == null) {
            noneExiled = true;
            return;
        }
        for (UUID imprinted : sourceObject.getImprinted()){
            if (imprinted != null && exileZone.contains(imprinted)){
                Card card = game.getCard(imprinted);
                if (card != null) {
                    colorsAmongImprinted = colorsAmongImprinted.union(card.getColor(game));
                }
            }
        }
        FilterObject filterObject = new FilterObject("a source of your choice that shares a color with the exiled card");
        filterObject.add(new SharesColorPredicate(colorsAmongImprinted));
        this.target = new TargetSource(filterObject);
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        if (target.getFirstTarget() != null) {
            mageObjectReference = new MageObjectReference(target.getFirstTarget(), game);
        } else {
            mageObjectReference = null;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (noneExiled || mageObjectReference == null){
            return false;
        }
        if (super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            return mageObjectReference.refersTo(mageObject, game);
        }
        return false;
    }
}