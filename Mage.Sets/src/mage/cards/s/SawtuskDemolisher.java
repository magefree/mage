package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawtuskDemolisher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SawtuskDemolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {3}{G}
        this.addAbility(new MutateAbility(this, "{3}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature mutates, destroy target noncreature permanent. Its controller creates a 3/3 green Beast creature token.
        Ability ability = new MutatesSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new CreateTokenControllerTargetPermanentEffect(new BeastToken()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SawtuskDemolisher(final SawtuskDemolisher card) {
        super(card);
    }

    @Override
    public SawtuskDemolisher copy() {
        return new SawtuskDemolisher(this);
    }
}

class SawtuskDemolisherEffect extends OneShotEffect {

    SawtuskDemolisherEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target noncreature permanent. Its controller creates a 3/3 green Beast creature token.";
    }

    private SawtuskDemolisherEffect(final SawtuskDemolisherEffect effect) {
        super(effect);
    }

    @Override
    public SawtuskDemolisherEffect copy() {
        return new SawtuskDemolisherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game, false);
        if (player == null) {
            return false;
        }
        Effect effect = new CreateTokenTargetEffect(new BeastToken());
        effect.setTargetPointer(new FixedTarget(player.getId(), game));
        return effect.apply(game, source);
    }
}
