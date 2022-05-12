
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class DauntlessBodyguard extends CardImpl {

    public DauntlessBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Dauntless Bodyguard enters the battlefield, choose another creature you control.
        this.addAbility(new AsEntersBattlefieldAbility(new DauntlessBodyguardChooseCreatureEffect()));

        // Sacrifice Dauntless Bodyguard: The chosen creature gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DauntlessBodyguardGainAbilityEffect(), new SacrificeSourceCost()));

    }

    private DauntlessBodyguard(final DauntlessBodyguard card) {
        super(card);
    }

    @Override
    public DauntlessBodyguard copy() {
        return new DauntlessBodyguard(this);
    }
}

class DauntlessBodyguardChooseCreatureEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DauntlessBodyguardChooseCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose another creature you control";
    }

    public DauntlessBodyguardChooseCreatureEffect(final DauntlessBodyguardChooseCreatureEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessBodyguardChooseCreatureEffect copy() {
        return new DauntlessBodyguardChooseCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent mageObject = game.getPermanentEntering(source.getSourceId());
        if (controller != null && mageObject != null) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, filter, true);
            if (controller.choose(this.outcome, target, source, game)) {
                Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
                if (chosenCreature != null) {
                    game.getState().setValue(mageObject.getId() + "_chosenCreature", new MageObjectReference(chosenCreature, game));
                    mageObject.addInfo("chosen creature", CardUtil.addToolTipMarkTags("Chosen creature: " + chosenCreature.getIdName()), game);
                }
            }
            return true;
        }
        return false;
    }
}

class DauntlessBodyguardGainAbilityEffect extends OneShotEffect {

    public DauntlessBodyguardGainAbilityEffect() {
        super(Outcome.AddAbility);
        this.staticText = "The chosen creature gains indestructible until end of turn";
    }

    public DauntlessBodyguardGainAbilityEffect(final DauntlessBodyguardGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessBodyguardGainAbilityEffect copy() {
        return new DauntlessBodyguardGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        MageObjectReference mor = (MageObjectReference) game.getState().getValue(sourcePermanent.getId() + "_chosenCreature");
        if (mor == null) {
            return false;
        }
        Permanent chosenPermanent = mor.getPermanent(game);
        if (chosenPermanent != null) {
            ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(chosenPermanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
