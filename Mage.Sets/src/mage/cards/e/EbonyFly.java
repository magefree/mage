package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EbonyFly extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EbonyFly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Ebony Fly enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}: Roll a d6. Until end of turn, you may have Ebony Fly become an X/X Insect artifact creature with flying, where X is the result.
        this.addAbility(new SimpleActivatedAbility(new EbonyFlyEffect(), new GenericManaCost(4)));

        // Whenever Ebony Fly attacks, another target attacking creature gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EbonyFly(final EbonyFly card) {
        super(card);
    }

    @Override
    public EbonyFly copy() {
        return new EbonyFly(this);
    }
}

class EbonyFlyEffect extends OneShotEffect {

    EbonyFlyEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d6. Until end of turn, you may have {this} " +
                "become an X/X Insect artifact creature with flying, where X is the result";
    }

    private EbonyFlyEffect(final EbonyFlyEffect effect) {
        super(effect);
    }

    @Override
    public EbonyFlyEffect copy() {
        return new EbonyFlyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !player.chooseUse(
                outcome, "Have " + permanent.getName() + " become a "
                        + result + '/' + result + " creature until end of turn?", source, game
        )) {
            return true;
        }
        game.addEffect(new BecomesCreatureSourceEffect(
                new CreatureToken(result, result)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                "", Duration.EndOfTurn, false, false
        ), source);
        return true;
    }
}
