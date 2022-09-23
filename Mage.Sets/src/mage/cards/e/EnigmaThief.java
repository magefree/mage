package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnigmaThief extends CardImpl {

    public EnigmaThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Prowl {3}{U}
        this.addAbility(new ProwlAbility(this, "{3}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Enigma Thief enters the battlefield, for each opponent, return up to one target nonland permanent that player controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, return up to one target nonland permanent that player controls to its owner's hand"));
        ability.setTargetAdjuster(EnigmaThiefAdjuster.instance);
        this.addAbility(ability);
    }

    private EnigmaThief(final EnigmaThief card) {
        super(card);
    }

    @Override
    public EnigmaThief copy() {
        return new EnigmaThief(this);
    }
}

enum EnigmaThiefAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterNonlandPermanent("nonland permanent controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}
