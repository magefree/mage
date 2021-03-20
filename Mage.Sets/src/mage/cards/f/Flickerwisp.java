package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author Plopman
 */
public final class Flickerwisp extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Flickerwisp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Flickerwisp enters the battlefield, exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FlickerwispEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Flickerwisp(final Flickerwisp card) {
        super(card);
    }

    @Override
    public Flickerwisp copy() {
        return new Flickerwisp(this);
    }
}

class FlickerwispEffect extends OneShotEffect {

    public FlickerwispEffect() {
        super(Outcome.Detriment);
        staticText = "exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public FlickerwispEffect(final FlickerwispEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && permanent != null && sourcePermanent != null) {
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourcePermanent.getIdName(), source, game, Zone.BATTLEFIELD, true)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public FlickerwispEffect copy() {
        return new FlickerwispEffect(this);
    }
}
