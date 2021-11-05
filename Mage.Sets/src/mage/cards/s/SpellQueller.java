
package mage.cards.s;

import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public final class SpellQueller extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public SpellQueller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Spell Queller enters the battlefied, exile target spell with converted mana cost 4 or less.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new SpellQuellerEntersEffect(), false);
        ability1.addTarget(new TargetSpell(filter));
        this.addAbility(ability1);

        // When Spell Queller leaves the battlefield, the exiled card's owner may cast that card without paying its mana cost.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new SpellQuellerLeavesEffect(), false);
        this.addAbility(ability2);
    }

    private SpellQueller(final SpellQueller card) {
        super(card);
    }

    @Override
    public SpellQueller copy() {
        return new SpellQueller(this);
    }
}

class SpellQuellerEntersEffect extends OneShotEffect {

    public SpellQuellerEntersEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target spell with mana value 4 or less";
    }

    public SpellQuellerEntersEffect(final SpellQuellerEntersEffect effect) {
        super(effect);
    }

    @Override
    public SpellQuellerEntersEffect copy() {
        return new SpellQuellerEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                return controller.moveCardsToExile(spell, source, game, true, exileId, sourceObject.getIdName());
            }
            return true;
        }
        return false;
    }
}

class SpellQuellerLeavesEffect extends OneShotEffect {

    public SpellQuellerLeavesEffect() {
        super(Outcome.Benefit);
        this.staticText = "the exiled card's owner may cast that card without paying its mana cost";
    }

    public SpellQuellerLeavesEffect(final SpellQuellerLeavesEffect effect) {
        super(effect);
    }

    @Override
    public SpellQuellerLeavesEffect copy() {
        return new SpellQuellerLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
            if (permanentLeftBattlefield == null) {
                Logger.getLogger(ReturnFromExileForSourceEffect.class).error("Permanent not found: " + sourceObject.getName());
                return false;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game)));
            if (exile != null) { // null is valid if source left battlefield before enters the battlefield effect resolved
                Card card = null;
                Set<Card> exiledCards = exile.getCards(game);
                if (exiledCards != null && !exiledCards.isEmpty()) {
                    card = exiledCards.iterator().next();
                    if (card != null) {
                        Player cardOwner = game.getPlayer(card.getOwnerId());
                        if (cardOwner != null) {
                            if (cardOwner.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + " without paying cost?", source, game)) {
                                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                                cardOwner.cast(cardOwner.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
