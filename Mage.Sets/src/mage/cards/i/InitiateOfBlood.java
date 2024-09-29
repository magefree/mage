package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author awjackson
 */
public final class InitiateOfBlood extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public InitiateOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OGRE, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Goka the Unjust";

        // {T}: Initiate of Blood deals 1 damage to target creature that was dealt damage this turn. 
        // When that creature dies this turn, flip Initiate of Blood.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(
                new FlipSourceEffect(new GokaTheUnjust()).setText("flip {this}")
        )));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private InitiateOfBlood(final InitiateOfBlood card) {
        super(card);
    }

    @Override
    public InitiateOfBlood copy() {
        return new InitiateOfBlood(this);
    }
}

class GokaTheUnjust extends TokenImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    GokaTheUnjust() {
        super("Goka the Unjust", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.OGRE, SubType.SHAMAN);
        power = new MageInt(4);
        toughness = new MageInt(4);

        // {T}: Goka the Unjust deals 4 damage to target creature that was dealt damage this turn.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(4), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }
    private GokaTheUnjust(final GokaTheUnjust token) {
        super(token);
    }

    public GokaTheUnjust copy() {
        return new GokaTheUnjust(this);
    }
}
