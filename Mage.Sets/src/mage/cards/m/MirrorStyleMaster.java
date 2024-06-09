package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorStyleMaster extends CardImpl {

    public MirrorStyleMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Whenever this creature attacks, for each attacking modified creature you control, create a tapped and attacking token that's a copy of that creature. Exile those tokens at end of combat.
        backupAbility.addAbility(new AttacksTriggeredAbility(new MirrorStyleMasterEffect())
                .setTriggerPhrase("Whenever this creature attacks, "));
    }

    private MirrorStyleMaster(final MirrorStyleMaster card) {
        super(card);
    }

    @Override
    public MirrorStyleMaster copy() {
        return new MirrorStyleMaster(this);
    }
}

class MirrorStyleMasterEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(ModifiedPredicate.instance);
    }

    MirrorStyleMasterEffect() {
        super(Outcome.Benefit);
        staticText = "for each attacking modified creature you control, create a tapped and attacking token " +
                "that's a copy of that creature. Exile those tokens at end of combat";
    }

    private MirrorStyleMasterEffect(final MirrorStyleMasterEffect effect) {
        super(effect);
    }

    @Override
    public MirrorStyleMasterEffect copy() {
        return new MirrorStyleMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    null, null, false, 1, true, true
            );
            effect.setSavedPermanent(permanent);
            effect.apply(game, source);
            permanents.addAll(effect.getAddedPermanents());
        }
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new ExileTargetEffect()
                        .setTargetPointer(new FixedTargets(permanents, game))
                        .setText("exile those tokens")
        ), source);
        return true;
    }
}
