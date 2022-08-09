package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordXanderTheCollector extends CardImpl {

    public LordXanderTheCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Lord Xander, the Collector enters the battlefield, target opponent discards half the cards in their hand, rounded down.
        Ability ability = new EntersBattlefieldTriggeredAbility(LordXanderTheCollectorEffectType.DISCARD.makeEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever Lord Xander attacks, defending player mills half their library, rounded down.
        this.addAbility(new AttacksTriggeredAbility(
                LordXanderTheCollectorEffectType.MILL.makeEffect(),
                false, null, SetTargetPointer.PLAYER
        ));

        // When Lord Xander dies, target opponent sacrifices half the nonland permanents they control, rounded down.
        ability = new DiesSourceTriggeredAbility(LordXanderTheCollectorEffectType.SACRIFICE.makeEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private LordXanderTheCollector(final LordXanderTheCollector card) {
        super(card);
    }

    @Override
    public LordXanderTheCollector copy() {
        return new LordXanderTheCollector(this);
    }
}

enum LordXanderTheCollectorEffectType {
    DISCARD, MILL, SACRIFICE;

    LordXanderTheCollectorEffect makeEffect() {
        return new LordXanderTheCollectorEffect(this);
    }
}

class LordXanderTheCollectorEffect extends OneShotEffect {

    private final LordXanderTheCollectorEffectType effectType;

    LordXanderTheCollectorEffect(LordXanderTheCollectorEffectType LordXanderTheCollectorEffectType) {
        super(Outcome.Benefit);
        this.effectType = LordXanderTheCollectorEffectType;
    }

    private LordXanderTheCollectorEffect(final LordXanderTheCollectorEffect effect) {
        super(effect);
        this.effectType = effect.effectType;
    }

    @Override
    public LordXanderTheCollectorEffect copy() {
        return new LordXanderTheCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count;
        switch (effectType) {
            case DISCARD:
                count = player.getHand().size();
                if (count < 2) {
                    return false;
                }
                player.discard(count / 2, false, false, source, game);
                return true;
            case MILL:
                count = player.getLibrary().size();
                if (count < 2) {
                    return false;
                }
                player.millCards(count / 2, source, game);
                return true;
            case SACRIFICE:
                count = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, player.getId(), game).size();
                if (count < 2) {
                    return false;
                }
                TargetPermanent target = new TargetPermanent(
                        count / 2, StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND
                );
                target.setNotTarget(true);
                target.withChooseHint("sacrifice");
                target.setRequired(true);
                player.choose(outcome, target, source, game);
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null) {
                        permanent.sacrifice(source, game);
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        switch (effectType) {
            case DISCARD:
                return "target opponent discards half the cards in their hand, rounded down";
            case MILL:
                return "defending player mills half their library, rounded down";
            case SACRIFICE:
                return "target opponent sacrifices half the nonland permanents they control, rounded down";
        }
        return "";
    }
}
