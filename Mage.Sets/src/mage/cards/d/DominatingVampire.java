package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DominatingVampire extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with mana value less than or equal to the number of Vampires you control");

    static {
        filter.add(DominatingVampirePredicate.instance);
    }

    public DominatingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Dominating Vampire enters the battlefield, gain control of target creature with mana value less than or equal to the number of Vampires you control until end of turn.
        // Untap that creature. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn));
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, "It gains haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private DominatingVampire(final DominatingVampire card) {
        super(card);
    }

    @Override
    public DominatingVampire copy() {
        return new DominatingVampire(this);
    }
}

enum DominatingVampirePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        int vampires = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(input.getPlayerId())) {
            if (permanent.hasSubtype(SubType.VAMPIRE, game)) {
                vampires++;
            }
        }
        return input.getObject().getManaValue() <= vampires;
    }
}
