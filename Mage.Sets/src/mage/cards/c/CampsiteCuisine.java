package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CampsiteCuisine extends CardImpl {

    public CampsiteCuisine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever this enchantment or a legendary creature you control enters, create a Food token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), StaticFilters.FILTER_CREATURE_LEGENDARY, false, true
        ).setTriggerPhrase("Whenever this enchantment or a legendary creature you control enters, "));

        // Whenever you attack, you may sacrifice X Foods. When you do, up to X target attacking creatures each get +3/+3 and gain trample and indestructible until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new CampsiteCuisineEffect(), 1));
    }

    private CampsiteCuisine(final CampsiteCuisine card) {
        super(card);
    }

    @Override
    public CampsiteCuisine copy() {
        return new CampsiteCuisine(this);
    }
}

class CampsiteCuisineEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FOOD, "Foods");

    CampsiteCuisineEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice X Foods. When you do, up to X target attacking creatures " +
                "each get +3/+3 and gain trample and indestructible until end of turn";
    }

    private CampsiteCuisineEffect(final CampsiteCuisineEffect effect) {
        super(effect);
    }

    @Override
    public CampsiteCuisineEffect copy() {
        return new CampsiteCuisineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(0, Integer.MAX_VALUE, filter);
        player.choose(Outcome.Sacrifice, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                count++;
            }
        }
        if (count < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new BoostTargetEffect(3, 3)
                .setText("up to X target attacking creatures each get +3/+3"), false);
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gain trample"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        ability.addTarget(new TargetAttackingCreature(0, count));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
