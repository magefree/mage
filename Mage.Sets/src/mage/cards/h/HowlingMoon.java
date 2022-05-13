package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class HowlingMoon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(), SubType.WEREWOLF.getPredicate()));
    }

    public HowlingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of combat on your turn, target Wolf or Werewolf you control gets +2/+2 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

        // Whenever an opponent casts their second spell each turn, create a 2/2 green Wolf creature token.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new CreateTokenEffect(new WolfToken()), TargetController.OPPONENT
        ));
    }

    private HowlingMoon(final HowlingMoon card) {
        super(card);
    }

    @Override
    public HowlingMoon copy() {
        return new HowlingMoon(this);
    }
}
