package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class EchoingAssault extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature that's attacking that player");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(EchoingAssaultPredicate.instance);
    }

    public EchoingAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");


        // Creature tokens you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // Whenever you attack a player, choose target nontoken creature that's attacking that player. Create a token that's a copy of that creature, except it's 1/1. The token enters tapped and attacking that player. Sacrifice it at the beginning of the next end step.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(new EchoingAssaultEffect(), SetTargetPointer.NONE);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EchoingAssault(final EchoingAssault card) {
        super(card);
    }

    @Override
    public EchoingAssault copy() {
        return new EchoingAssault(this);
    }
}

enum EchoingAssaultPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil.getEffectValueFromAbility(input.getSource(), "playerAttacked", UUID.class)
                .filter(uuid -> uuid.equals(game.getCombat().getDefenderId(input.getObject().getId())))
                .isPresent();
    }
}

class EchoingAssaultEffect extends OneShotEffect {

    EchoingAssaultEffect() {
        super(Outcome.Benefit);
        staticText = "choose target nontoken creature that's attacking that player. Create a token that's a copy of that creature, except it's 1/1. The token enters tapped and attacking that player. Sacrifice it at the beginning of the next end step.";
    }

    private EchoingAssaultEffect(final EchoingAssaultEffect effect) {
        super(effect);
    }

    @Override
    public EchoingAssaultEffect copy() {
        return new EchoingAssaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object attackedPlayer = getValue("playerAttacked");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !(attackedPlayer instanceof UUID)) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, false,
                1, true, true, (UUID) attackedPlayer, 1, 1, false);
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
