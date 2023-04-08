package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RankleAndTorbran extends CardImpl {

    public RankleAndTorbran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Rankle and Torbran deals combat damage to a player or battle, choose any number --
        // * Each player creates a Treasure token.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenAllEffect(new TreasureToken(), TargetController.EACH_PLAYER), false
        ).setOrBattle(true);
        ability.getModes().setMinModes(0);
        ability.getModes().setMinModes(3);

        // * Each player sacrifices a creature.
        ability.addMode(new Mode(new SacrificeAllEffect(1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // * If a source would deal damage to a player or battle this turn, it deals that much damage plus 2 instead.
        ability.addMode(new Mode(new RankleAndTorbranEffect()));
        this.addAbility(ability);
    }

    private RankleAndTorbran(final RankleAndTorbran card) {
        super(card);
    }

    @Override
    public RankleAndTorbran copy() {
        return new RankleAndTorbran(this);
    }
}

class RankleAndTorbranEffect extends ReplacementEffectImpl {

    RankleAndTorbranEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source would deal damage to a player or battle this turn, it deals that much damage plus 2 instead";
    }

    private RankleAndTorbranEffect(final RankleAndTorbranEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() < 1) {
            return false;
        }
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            return true;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isBattle(game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public RankleAndTorbranEffect copy() {
        return new RankleAndTorbranEffect(this);
    }
}
