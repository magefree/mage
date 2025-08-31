package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth & L_J
 */
public final class Spawnbroker extends CardImpl {

    private static final String rule = "you may exchange control of target creature you control and target creature with power less than or equal to that creature's power an opponent controls";

    public Spawnbroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Spawnbroker enters the battlefield, you may exchange control of target creature you control and target creature with power less than or equal to that creature's power an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE));
        ability.addTarget(new SpawnbrokerSecondTarget());
        this.addAbility(ability);

    }

    private Spawnbroker(final Spawnbroker card) {
        super(card);
    }

    @Override
    public Spawnbroker copy() {
        return new Spawnbroker(this);
    }
}

class SpawnbrokerSecondTarget extends TargetPermanent {

    public SpawnbrokerSecondTarget() {
        super(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        withTargetName("creature with power less than or equal to that creature's power an opponent controls");
    }

    private SpawnbrokerSecondTarget(final SpawnbrokerSecondTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        Permanent possiblePermanent = game.getPermanent(id);
        if (ownPermanent == null || possiblePermanent == null) {
            return false;
        }
        return super.canTarget(id, source, game) && ownPermanent.getPower().getValue() >= possiblePermanent.getPower().getValue();
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (ownPermanent == null) {
                // playable or first target not yet selected
                // use all
                possibleTargets.add(permanent.getId());
            } else {
                // real
                // filter by power
                if (ownPermanent.getPower().getValue() >= permanent.getPower().getValue()) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        possibleTargets.removeIf(id -> ownPermanent != null && ownPermanent.getId().equals(id));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        // AI hint with better outcome
        return super.chooseTarget(Outcome.GainControl, playerId, source, game);
    }

    @Override
    public SpawnbrokerSecondTarget copy() {
        return new SpawnbrokerSecondTarget(this);
    }
}
