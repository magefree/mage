package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.ParadigmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EchocastingSymposium extends CardImpl {

    public EchocastingSymposium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        this.subtype.add(SubType.LESSON);

        // Target player creates a token that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new EchocastingSymposiumEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Paradigm
        this.addAbility(new ParadigmAbility());
    }

    private EchocastingSymposium(final EchocastingSymposium card) {
        super(card);
    }

    @Override
    public EchocastingSymposium copy() {
        return new EchocastingSymposium(this);
    }
}

class EchocastingSymposiumEffect extends OneShotEffect {

    EchocastingSymposiumEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "target player creates a token that's a copy of target creature you control";
    }

    private EchocastingSymposiumEffect(final EchocastingSymposiumEffect effect) {
        super(effect);
    }

    @Override
    public EchocastingSymposiumEffect copy() {
        return new EchocastingSymposiumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targets = this.getTargetPointer().getTargets(game, source);
        if (targets.size() != 2) {
            return false;
        }
        Player player = game.getPlayer(targets.get(0));
        Permanent permanent = game.getPermanent(targets.get(1));
        return player != null
                && permanent != null
                && new CreateTokenCopyTargetEffect(player.getId())
                .setSavedPermanent(permanent)
                .apply(game, source);
    }
}
