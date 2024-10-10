package mage.cards.h;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaOptions;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class HeirloomEpic extends CardImpl {

    public HeirloomEpic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {4}, {T}: Draw a card. For each mana in this ability's activation cost, you may tap an untapped creature you control rather than pay that mana. Activate only as a sorcery.
        this.addAbility(new HeirloomEpicActivatedAbility());
    }

    private HeirloomEpic(final HeirloomEpic card) {
        super(card);
    }

    @Override
    public HeirloomEpic copy() {
        return new HeirloomEpic(this);
    }
}

class HeirloomEpicActivatedAbility extends ActivateAsSorceryActivatedAbility {
    HeirloomEpicActivatedAbility() {
        super(new DrawCardSourceControllerEffect(1), new GenericManaCost(4));
        addCost(new TapSourceCost());
        addSubAbility(new HeirloomEpicPaymentAbility());
    }

    protected HeirloomEpicActivatedAbility(final HeirloomEpicActivatedAbility ability) {
        super(ability);
    }

    @Override
    public HeirloomEpicActivatedAbility copy() {
        return new HeirloomEpicActivatedAbility(this);
    }

    @Override
    public String getRule() {
        String superRule = super.getRule();
        return superRule.replaceFirst("Activate only as a sorcery",
                "For each mana in this ability's activation cost, you may tap an untapped creature you control rather than pay that mana. Activate only as a sorcery");
    }
}

// Based on ImproviseAbility
class HeirloomEpicPaymentAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterControlledCreaturePermanent filterUntapped = new FilterControlledCreaturePermanent();

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final DynamicValue untappedCount = new PermanentsOnBattlefieldCount(filterUntapped);

    public HeirloomEpicPaymentAbility() {
        super(Zone.ALL, null); // all AlternateManaPaymentAbility must use ALL zone to calculate playable abilities
        this.setRuleAtTheTop(true);
        this.addHint(new ValueHint("Untapped creatures you control", untappedCount));
    }

    protected HeirloomEpicPaymentAbility(final HeirloomEpicPaymentAbility ability) {
        super(ability);
    }

    @Override
    public HeirloomEpicPaymentAbility copy() {
        return new HeirloomEpicPaymentAbility(this);
    }

    @Override
    public String getRule() {
        return "";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.AFTER;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        int canPayCount = untappedCount.calculate(game, source, null);
        if (controller != null && canPayCount > 0) {
            if (appliesToAbility(source) && unpaid.getMana().getGeneric() > 0) {
                SpecialAction specialAction = new HeirloomEpicSpecialAction(unpaid, this);
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());
                // create filter for possible creatures to tap
                Target target = new TargetControlledPermanent(1, unpaid.getMana().getGeneric(), filterUntapped, true);
                target.withTargetName("creature to tap to pay ability cost");
                specialAction.addTarget(target);
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();
        Player controller = game.getPlayer(source.getControllerId());
        int canPayCount = untappedCount.calculate(game, source, null);
        if (controller != null && canPayCount > 0) {
            options.addMana(Mana.GenericMana(Math.min(unpaid.getMana().getGeneric(), canPayCount)));
        }
        return options;
    }

    @Override
    public boolean appliesToAbility(Ability ability) {
        return ability instanceof HeirloomEpicActivatedAbility;
    }
}

class HeirloomEpicSpecialAction extends SpecialAction {

    public HeirloomEpicSpecialAction(ManaCost unpaid, AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        setRuleVisible(false);
        this.addEffect(new HeirloomEpicEffect(unpaid));
    }

    protected HeirloomEpicSpecialAction(final HeirloomEpicSpecialAction ability) {
        super(ability);
    }

    @Override
    public HeirloomEpicSpecialAction copy() {
        return new HeirloomEpicSpecialAction(this);
    }
}

class HeirloomEpicEffect extends OneShotEffect {

    private final ManaCost unpaid;

    public HeirloomEpicEffect(ManaCost unpaid) {
        super(Outcome.Benefit);
        this.unpaid = unpaid;
        this.staticText = "For each mana in this ability’s activation cost, you may tap an untapped creature you control rather than pay that mana";
    }

    protected HeirloomEpicEffect(final HeirloomEpicEffect effect) {
        super(effect);
        this.unpaid = effect.unpaid;
    }

    @Override
    public HeirloomEpicEffect copy() {
        return new HeirloomEpicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        StackObject ability = game.getStack().getStackObject(source.getSourceId());
        if (controller == null || ability == null) {
            return false;
        }
        for (UUID creatureId : this.getTargetPointer().getTargets(game, source)) {
            Permanent perm = game.getPermanent(creatureId);
            if (perm == null) {
                continue;
            }
            if (!perm.isTapped() && perm.tap(source, game)) {
                ManaPool manaPool = controller.getManaPool();
                manaPool.addMana(Mana.ColorlessMana(1), game, source);
                manaPool.unlockManaType(ManaType.COLORLESS);
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " taps " + perm.getLogName() + " to pay {1}");
                }

                // can't use mana abilities after that (tap cost must be paid after mana abilities only)
                ability.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.AFTER);
            }
        }
        return true;
    }
}
