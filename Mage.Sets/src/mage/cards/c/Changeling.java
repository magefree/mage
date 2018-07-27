package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.constants.*;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class Changeling extends CardImpl {

    public Changeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Changeling can't be blocked unless defending player pays {1} for each other attacking creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChangelingEffect()));

        // Whenever Changeling attacks and isn't blocked, you draw a card and lose 1 life.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability);
    }

    public Changeling(final Changeling card) {
        super(card);
    }

    @Override
    public Changeling copy() {
        return new Changeling(this);
    }
}

class ChangelingEffect extends RestrictionEffect {

    public ChangelingEffect() {
        this(Duration.WhileOnBattlefield);
    }
    public ChangelingEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can't be blocked unless defending player pays {1} for each other attacking creature";
    }

    public ChangelingEffect(ChangelingEffect effect) {
        super(effect);
    }

    @Override
    public ChangelingEffect copy() {
        return new ChangelingEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Player player = game.getPlayer(blocker.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            List<Permanent> attackingPermanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_ATTACKING_CREATURES, game);
            if(attackingPermanents.size() <= 1) { // Can be blocked for free if it's the only attacker
                return true;
            }
            Cost cost = new GenericManaCost(attackingPermanents.size() - 1);
            boolean result = false;
            String message = "Pay " + cost.getText() + " to block Changeling?";
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                int bookmark = game.bookmarkState();
                if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    result = true;
                    player.resetStoredBookmark(game);
                } else {
                    game.restoreState(bookmark, ChangelingEffect.class.getName());
                }
            }
            return result;
        }
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}
