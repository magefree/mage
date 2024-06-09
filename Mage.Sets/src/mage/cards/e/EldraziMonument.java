package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class EldraziMonument extends CardImpl {

    public EldraziMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Creatures you control get +1/+1, have flying, and are indestructible.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and have flying"));
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and indestructible"));
        this.addAbility(ability);

        // At the beginning of your upkeep, sacrifice a creature. If you can't, sacrifice Eldrazi Monument.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EldraziMonumentEffect(), TargetController.YOU, false));
    }

    private EldraziMonument(final EldraziMonument card) {
        super(card);
    }

    @Override
    public EldraziMonument copy() {
        return new EldraziMonument(this);
    }
}

class EldraziMonumentEffect extends OneShotEffect {

    EldraziMonumentEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice a creature. If you can't, sacrifice {this}";
    }

    private EldraziMonumentEffect(final EldraziMonumentEffect effect) {
        super(effect);
    }

    @Override
    public EldraziMonumentEffect copy() {
        return new EldraziMonumentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Sacrifice a creature
        TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_PERMANENT_CREATURE);
        if (target.canChoose(controller.getId(), source, game)) {
            controller.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                return permanent.sacrifice(source, game);
            }
        }
        // If you can't, sacrifice Eldrazi Monument
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.sacrifice(source, game);
        }
        return false;
    }

}
