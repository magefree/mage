
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DarksteelGarrison extends CardImpl {

    public DarksteelGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.FORTIFICATION);

        // Fortified land is indestructible.
        Ability gainedAbility = IndestructibleAbility.getInstance();
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Fortified land has indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever fortified land becomes tapped, target creature gets +1/+1 until end of turn.
        Ability ability = new BecomesTappedAttachedTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), "fortified land");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Fortify {3}
        this.addAbility(new FortifyAbility(Outcome.AddAbility, new GenericManaCost(3)));

    }

    public DarksteelGarrison(final DarksteelGarrison card) {
        super(card);
    }

    @Override
    public DarksteelGarrison copy() {
        return new DarksteelGarrison(this);
    }
}

class FortifyAbility extends ActivatedAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("land you control");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public FortifyAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetControlledPermanent(filter));
    }

    public FortifyAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Fortify"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ActivationStatus activationStatus = super.canActivate(playerId, game);
        if (activationStatus.canActivate()) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.hasSubtype(SubType.FORTIFICATION, game)) {
                return activationStatus;
            }
        }
        return ActivationStatus.getFalse();
    }

    public FortifyAbility(final FortifyAbility ability) {
        super(ability);
    }

    @Override
    public FortifyAbility copy() {
        return new FortifyAbility(this);
    }

    @Override
    public String getRule() {
        return "Fortify " + costs.getText() + manaCosts.getText() + " (" + manaCosts.getText() + ": <i>Attach to target land you control. Fortify only as a sorcery.)</i>";
    }

}
