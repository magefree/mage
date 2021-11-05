package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CurseOfSurveillance extends CardImpl {

    public CurseOfSurveillance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, any number of target players other than that player each draw cards equal to the number of Curses attached to that player.
        ability = new BeginningOfUpkeepTriggeredAbility(
                new DrawCardTargetEffect(CurseOfSurveillanceValue.instance).setText(
                        "any number of target players other than that player each draw cards equal to the number of Curses attached to that player"
                ),
                TargetController.ENCHANTED, false
        );
        ability.setTargetAdjuster(CurseOfSurveillanceTargetAdjuster.instance);
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private CurseOfSurveillance(final CurseOfSurveillance card) {
        super(card);
    }

    @Override
    public CurseOfSurveillance copy() {
        return new CurseOfSurveillance(this);
    }
}

enum CurseOfSurveillanceValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int curses = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (permanent != null
                    && permanent.hasSubtype(SubType.CURSE, game)
                    && permanent.isAttachedTo(game.getActivePlayerId())) {
                curses++;
            }
        }
        return curses;
    }

    @Override
    public CurseOfSurveillanceValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "number of Curses attached to that player";
    }
}

enum CurseOfSurveillanceTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (enchantment != null) {
            FilterPlayer filter = new FilterPlayer();
            filter.add(Predicates.not(new PlayerIdPredicate(enchantment.getAttachedTo())));
            TargetPlayer target = new TargetPlayer(0, Integer.MAX_VALUE, false, filter);
            ability.getTargets().clear();
            ability.addTarget(target);
        }
    }
}
