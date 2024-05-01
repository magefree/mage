package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.functions.EmptyCopyApplier;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MarchFromVelisVel extends CardImpl {

    public MarchFromVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose a nonbasic land type. Each land you control of that type becomes a copy of target creature you control until end of turn and gains haste until end of turn.
        this.getSpellAbility().addEffect(new MarchFromVelisVelEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {4}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{U}")));
    }

    private MarchFromVelisVel(final MarchFromVelisVel card) {
        super(card);
    }

    @Override
    public MarchFromVelisVel copy() {
        return new MarchFromVelisVel(this);
    }
}

class MarchFromVelisVelEffect extends OneShotEffect {

    MarchFromVelisVelEffect() {
        super(Outcome.Benefit);
        staticText = "choose a nonbasic land type. Each land you control of that type becomes " +
                "a copy of target creature you control until end of turn and gains haste until end of turn";
    }

    private MarchFromVelisVelEffect(final MarchFromVelisVelEffect effect) {
        super(effect);
    }

    @Override
    public MarchFromVelisVelEffect copy() {
        return new MarchFromVelisVelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose a nonbasic land type");
        choice.setChoices(SubType.getNonbasicLandTypes().stream().map(SubType::toString).collect(Collectors.toSet()));
        player.choose(outcome, choice, game);
        SubType subType = SubType.fromString(choice.getChoice());
        if (subType == null) {
            return false;
        }
        FilterPermanent filter = new FilterControlledLandPermanent(subType, "");
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getSourceId(), source, game);
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent land : permanents) {
            game.copyPermanent(Duration.EndOfTurn, permanent, land.getId(), source, new EmptyCopyApplier());
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
