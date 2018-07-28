package mage.cards.s;

import java.util.*;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.ZoneChangeInfo;
import mage.game.ZonesHandler;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class StasisWard extends CardImpl {

    public StasisWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Stasis Ward enters the battlefield, counter target spell. If that spell is countered this way, exile that card until Stasis Ward leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new StasisWardCounterEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

        ability = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.GRAVEYARD), false);
        ability.setRuleVisible(false);
        this.addAbility(ability);
    }

    public StasisWard(final StasisWard card) {
        super(card);
    }

    @Override
    public StasisWard copy() {
        return new StasisWard(this);
    }
}

class StasisWardCounterEffect extends OneShotEffect {

    public StasisWardCounterEffect() {
        super(Outcome.Detriment);
        staticText = "counter target spell. If that spell is countered this way, exile that card until Stasis Ward leaves the battlefield";
    }

    public StasisWardCounterEffect(final StasisWardCounterEffect effect) {
        super(effect);
    }

    @Override
    public StasisWardCounterEffect copy() {
        return new StasisWardCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = new HashSet<>();
            for (Target target : source.getTargets()) {
                UUID targetId = target.getFirstTarget();
                Spell spell = game.getSpell(targetId);
                if (spell != null) {
                    Card card = spell.getCard();
                    if(card != null && game.getStack().counter(card.getId(), source.getSourceId(), game, Zone.EXILED, false, ZoneDetail.NONE)) {
                        cardsToExile.add(card);
                    }
                }
            }
            return controller.moveCardsToExile(cardsToExile, source, game, false,
                    CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), "Stasis Ward");
        }
        return false;
    }
}