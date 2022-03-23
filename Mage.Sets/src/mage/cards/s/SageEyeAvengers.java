
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SageEyeAvengers extends CardImpl {

    public SageEyeAvengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Sage-Eye Avengers attacks, you may return target creature to its owner's hand if its power is less than Sage-Eye Avengers's power.
        Ability ability = new AttacksTriggeredAbility(new SageEyeAvengersEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SageEyeAvengers(final SageEyeAvengers card) {
        super(card);
    }

    @Override
    public SageEyeAvengers copy() {
        return new SageEyeAvengers(this);
    }
}

class SageEyeAvengersEffect extends OneShotEffect {

    public SageEyeAvengersEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may return target creature to its owner's hand if its power is less than {this}'s power";
    }

    public SageEyeAvengersEffect(final SageEyeAvengersEffect effect) {
        super(effect);
    }

    @Override
    public SageEyeAvengersEffect copy() {
        return new SageEyeAvengersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null && targetCreature.getPower().getValue() < sourceObject.getPower().getValue()) {
                controller.moveCards(targetCreature, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
