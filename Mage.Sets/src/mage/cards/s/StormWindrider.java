package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author muz
 */
public final class StormWindrider extends CardImpl {

    public StormWindrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures with flying can't attack you or block creatures you control.
        this.addAbility(new SimpleStaticAbility(new StormWindriderEffect()));

        // Whenever you cast a spell that targets one or more creatures, those creatures gain flying until end of turn.
        this.addAbility(new StormWindriderTriggeredAbility());
    }

    private StormWindrider(final StormWindrider card) {
        super(card);
    }

    @Override
    public StormWindrider copy() {
        return new StormWindrider(this);
    }
}

class StormWindriderEffect extends RestrictionEffect {

    StormWindriderEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with flying can't attack you or block creatures you control";
    }

    private StormWindriderEffect(final StormWindriderEffect effect) {
        super(effect);
    }

    @Override
    public StormWindriderEffect copy() {
        return new StormWindriderEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent != null
            && permanent.isCreature(game)
            && permanent.getAbilities().contains(FlyingAbility.getInstance());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return defenderId == null || !defenderId.equals(source.getControllerId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return attacker == null || !attacker.isControlledBy(source.getControllerId());
    }
}

class StormWindriderTriggeredAbility extends SpellCastControllerTriggeredAbility {

    StormWindriderTriggeredAbility() {
        super(new GainAbilityTargetEffect(FlyingAbility.getInstance()), false);
    }

    private StormWindriderTriggeredAbility(final StormWindriderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StormWindriderTriggeredAbility copy() {
        return new StormWindriderTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        Set<Permanent> creatures = CardUtil
            .getAllSelectedTargets(spell.getStackAbility(), game)
            .stream()
            .map(game::getPermanent)
            .filter(Objects::nonNull)
            .filter(permanent -> permanent.isCreature(game))
            .collect(Collectors.toCollection(LinkedHashSet::new));
        if (creatures.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(creatures, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets one or more creatures, "
            + "those creatures gain flying until end of turn.";
    }
}
