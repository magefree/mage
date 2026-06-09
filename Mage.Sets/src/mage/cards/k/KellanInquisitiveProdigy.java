package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author DominionSpy
 */
public final class KellanInquisitiveProdigy extends AdventureCard {

    public KellanInquisitiveProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.FAERIE, SubType.DETECTIVE}, "{2}{G}{U}",
                "Tail the Suspect",
                new CardType[]{CardType.SORCERY}, "{G}{U}");

        // Kellan, Inquisitive Prodigy
        this.getLeftHalfCard().setPT(3, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever Kellan, Inquisitive Prodigy attacks, destroy up to one target artifact. If you controlled that permanent, draw a card.
        Ability ability = new AttacksTriggeredAbility(new KellanInquisitiveProdigyEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT));
        this.getLeftHalfCard().addAbility(ability);

        // Tail the Suspect
        // Investigate. You may play an additional land this turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new InvestigateEffect(false));
        this.getRightHalfCard().getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        finalizeCard();
    }

    private KellanInquisitiveProdigy(final KellanInquisitiveProdigy card) {
        super(card);
    }

    @Override
    public KellanInquisitiveProdigy copy() {
        return new KellanInquisitiveProdigy(this);
    }
}

class KellanInquisitiveProdigyEffect extends OneShotEffect {

    KellanInquisitiveProdigyEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to one target artifact. " +
                "If you controlled that permanent, draw a card";
    }

    private KellanInquisitiveProdigyEffect(final KellanInquisitiveProdigyEffect effect) {
        super(effect);
    }

    @Override
    public KellanInquisitiveProdigyEffect copy() {
        return new KellanInquisitiveProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }

        boolean isMine = permanent.isControlledBy(source.getControllerId());
        permanent.destroy(source, game, false);
        if (isMine) {
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
