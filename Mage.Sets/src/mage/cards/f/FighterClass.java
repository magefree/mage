package mage.cards.f;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FighterClass extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public FighterClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Fighter Class enters the battlefield, search your library for an Equipment card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // {1}{R}{W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{R}{W}"));

        // Equip abilities you activate cost {2} less to activate.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new AbilitiesCostReductionControllerEffect(
                        EquipAbility.class, "Equip", 2
                ).setText("equip abilities you activate cost {2} less to activate"), 2
        )));

        // {3}{R}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{R}{W}"));

        // Whenever a creature you control attacks, up to one target creature blocks it this combat if able.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new FighterClassEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private FighterClass(final FighterClass card) {
        super(card);
    }

    @Override
    public FighterClass copy() {
        return new FighterClass(this);
    }
}

class FighterClassEffect extends OneShotEffect {

    FighterClassEffect() {
        super(Outcome.Benefit);
        staticText = "up to one target creature blocks it this combat if able";
    }

    private FighterClassEffect(final FighterClassEffect effect) {
        super(effect);
    }

    @Override
    public FighterClassEffect copy() {
        return new FighterClassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference attackerRef = (MageObjectReference) getValue("attackerRef");
        if (attackerRef == null) {
            return false;
        }
        Permanent attacker = attackerRef.getPermanent(game);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (attacker == null || permanent == null) {
            return false;
        }
        game.addEffect(new FighterClassRequirementEffect(attackerRef).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

class FighterClassRequirementEffect extends RequirementEffect {

    private final MageObjectReference mor;

    public FighterClassRequirementEffect(MageObjectReference mor) {
        super(Duration.EndOfCombat);
        this.mor = mor;
    }

    private FighterClassRequirementEffect(final FighterClassRequirementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attacker = mor.getPermanent(game);
        if (attacker == null) {
            discard();
            return false;
        }
        return permanent != null
                && permanent.getId().equals(this.getTargetPointer().getFirst(game, source))
                && permanent.canBlock(source.getSourceId(), game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return mor.getSourceId();
    }

    @Override
    public FighterClassRequirementEffect copy() {
        return new FighterClassRequirementEffect(this);
    }
}
