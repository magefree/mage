package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class UnstableGlyphbridge extends CardImpl {

    public UnstableGlyphbridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");
        this.secondSideCardClazz = mage.cards.s.SandswirlWanderglyph.class;

        // When Unstable Glyphbridge enters the battlefield, if you cast it, for each player, choose a creature with power 2 or less that player controls. Then destroy all creatures except creatures chosen this way.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new UnstableGlyphbridgeEffect()), CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, " +
                    "for each player, choose a creature with power 2 or less that player controls. " +
                    "Then destroy all creatures except creatures chosen this way."
        ));

        // Craft with artifact {3}{W}{W}
        this.addAbility(new CraftAbility("{3}{W}{W}"));
    }

    private UnstableGlyphbridge(final UnstableGlyphbridge card) {
        super(card);
    }

    @Override
    public UnstableGlyphbridge copy() {
        return new UnstableGlyphbridge(this);
    }
}


class UnstableGlyphbridgeEffect extends OneShotEffect {
    UnstableGlyphbridgeEffect() {
        super(Outcome.Benefit);
        staticText = "for each player, choose a creature with power 2 or less that player controls. " +
                "Then destroy all creatures except creatures chosen this way.";
    }

    private UnstableGlyphbridgeEffect(final UnstableGlyphbridgeEffect effect) {
        super(effect);
    }

    @Override
    public UnstableGlyphbridgeEffect copy() {
        return new UnstableGlyphbridgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");
            filter.add(new PowerPredicate(ComparisonType.OR_LESS,2));
            filter.add(new ControllerIdPredicate(playerId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            target.withNotTarget(true);
            target.withChooseHint(player.getName() + " controls");

            controller.choose(Outcome.PutCreatureInPlay, target, source, game);
            cards.add(target.getFirstTarget());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)) {
            if (!cards.contains(permanent.getId())) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}