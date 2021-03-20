package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class GlimmerpointStag extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GlimmerpointStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Glimmerpoint Stag enters the battlefield, exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new GlimmerpointStagEffect());
        etbAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(etbAbility);
    }

    private GlimmerpointStag(final GlimmerpointStag card) {
        super(card);
    }

    @Override
    public GlimmerpointStag copy() {
        return new GlimmerpointStag(this);
    }
}

class GlimmerpointStagEffect extends OneShotEffect {

    private static final String effectText = "exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step";

    GlimmerpointStagEffect() {
        super(Outcome.Detriment);
        staticText = effectText;
    }

    GlimmerpointStagEffect(GlimmerpointStagEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                int zcc = permanent.getZoneChangeCounter(game);
                controller.moveCards(permanent, Zone.EXILED, source, game);
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                effect.setTargetPointer(new FixedTarget(permanent.getId(), zcc + 1));
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public GlimmerpointStagEffect copy() {
        return new GlimmerpointStagEffect(this);
    }

}
