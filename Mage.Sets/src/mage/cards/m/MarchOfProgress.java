package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MarchOfProgress extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public MarchOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect()
                .setText("Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it."));

        // Overload {6}{U}
        this.addAbility(new OverloadAbility(this, new MarchOfProgressOverloadEffect(), new ManaCostsImpl<>("{6}{U}")));
    }

    private MarchOfProgress(final MarchOfProgress card) {
        super(card);
    }

    @Override
    public MarchOfProgress copy() {
        return new MarchOfProgress(this);
    }
}

class MarchOfProgressOverloadEffect extends OneShotEffect {

    public MarchOfProgressOverloadEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose each artifact creature you control. For each creature chosen this way, create a token that's a copy of it";
    }

    public MarchOfProgressOverloadEffect(final MarchOfProgressOverloadEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfProgressOverloadEffect copy() {
        return new MarchOfProgressOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("each artifact creature you control");
            filter.add(CardType.ARTIFACT.getPredicate());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                if (permanent != null) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
