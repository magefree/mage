package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsecrateConsume extends SplitCard {

    public ConsecrateConsume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W/B}", "{2}{W}{B}", SpellAbilityType.SPLIT);

        // Consecrate
        // Exile target card from a graveyard.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard());

        // Draw a card.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // Consume
        // Target player sacrifices a creature with the greatest power among creatures they control. You gain life equal to its power.
        this.getRightHalfCard().getSpellAbility().addEffect(new ConsumeEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private ConsecrateConsume(final ConsecrateConsume card) {
        super(card);
    }

    @Override
    public ConsecrateConsume copy() {
        return new ConsecrateConsume(this);
    }
}

class ConsumeEffect extends OneShotEffect {

    ConsumeEffect() {
        super(Outcome.Benefit);
        staticText = "Target player sacrifices a creature " +
                "with the greatest power among creatures they control. " +
                "You gain life equal to its power.";
    }

    private ConsumeEffect(final ConsumeEffect effect) {
        super(effect);
    }

    @Override
    public ConsumeEffect copy() {
        return new ConsumeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || controller == null) {
            return false;
        }
        int greatestPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent != null && permanent.isCreature(game)) {
                greatestPower = Math.max(permanent.getPower().getValue(), greatestPower);
            }
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature with power " + greatestPower);
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, greatestPower));
        new SacrificeEffect(filter, 1, "").apply(game, source);
        controller.gainLife(greatestPower, game, source);
        return true;
    }
}