package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarkovEnforcer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE, "Vampire");

    public MarkovEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Markov Enforcer or another Vampire enters the battlefield under your control, Markov Enforcer fights up to one target creature an opponent controls.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(new FightTargetSourceEffect(), filter);
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever a creature dealt damage by Markov Enforcer this turn dies, create a Blood token.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new CreateTokenEffect(new BloodToken())));
    }

    private MarkovEnforcer(final MarkovEnforcer card) {
        super(card);
    }

    @Override
    public MarkovEnforcer copy() {
        return new MarkovEnforcer(this);
    }
}
