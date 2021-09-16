package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardasVanguard extends CardImpl {

    public SigardasVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sigarda's Vanguard enters the battlefield or attacks, choose any number of creatures with different powers. Those creatures gain double strike until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new GainAbilityTargetEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
                ).setText("choose any number of creatures with different powers. " +
                        "Those creatures gain double strike until end of turn")
        );
        ability.addTarget(new SigardasVanguardTarget());
        this.addAbility(ability.addHint(CovenHint.instance));
    }

    private SigardasVanguard(final SigardasVanguard card) {
        super(card);
    }

    @Override
    public SigardasVanguard copy() {
        return new SigardasVanguard(this);
    }
}

class SigardasVanguardTarget extends TargetCreaturePermanent {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with different powers");

    SigardasVanguardTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private SigardasVanguardTarget(final SigardasVanguardTarget target) {
        super(target);
    }

    @Override
    public SigardasVanguardTarget copy() {
        return new SigardasVanguardTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .noneMatch(p -> creature.getPower().getValue() == p);
    }
}
