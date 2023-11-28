package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TeyoToken;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class RammasEchorAncientShield extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }
    public RammasEchorAncientShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you cast your second spell each turn, draw a card, then create a 0/3 white Wall creature token with defender.
        Ability ability = new CastSecondSpellTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new TeyoToken()).concatBy(", then"));
        this.addAbility(ability);

        // At the beginning of combat on your turn, creatures you control with defender gain exalted until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilityControlledEffect(new ExaltedAbility(), Duration.EndOfTurn, filter), TargetController.YOU, false));
    }

    private RammasEchorAncientShield(final RammasEchorAncientShield card) {
        super(card);
    }

    @Override
    public RammasEchorAncientShield copy() {
        return new RammasEchorAncientShield(this);
    }
}
