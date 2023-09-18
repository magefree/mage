package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeddingInvitation extends CardImpl {

    public WeddingInvitation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Wedding Invitation enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {T}, Sacrifice Wedding Invitation: Target creature can't be blocked this turn. If it's a Vampire, it also gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new WeddingInvitationEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WeddingInvitation(final WeddingInvitation card) {
        super(card);
    }

    @Override
    public WeddingInvitation copy() {
        return new WeddingInvitation(this);
    }
}

class WeddingInvitationEffect extends OneShotEffect {

    WeddingInvitationEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Vampire, it also gains lifelink until end of turn";
    }

    private WeddingInvitationEffect(final WeddingInvitationEffect effect) {
        super(effect);
    }

    @Override
    public WeddingInvitationEffect copy() {
        return new WeddingInvitationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !permanent.hasSubtype(SubType.VAMPIRE, game)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}
