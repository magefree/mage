package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public final class SkyfireKirin extends CardImpl {

    public SkyfireKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIRIN);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may gain control of target creature with that spell's converted mana cost until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new SkyfireKirinEffect(),
                StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(SkyfireKirinAdjuster.instance);
        this.addAbility(ability);
    }

    private SkyfireKirin(final SkyfireKirin card) {
        super(card);
    }

    @Override
    public SkyfireKirin copy() {
        return new SkyfireKirin(this);
    }
}

enum SkyfireKirinAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Spell spell = game.getStack().getSpell(ability.getEffects().get(0).getTargetPointer().getFirst(game, ability));
        if (spell != null) {
            int cmc = spell.getManaValue();
            ability.getTargets().clear();
            FilterPermanent filter = new FilterCreaturePermanent("creature with mana value " + cmc);
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
            ability.addTarget(new TargetPermanent(filter));
        }
    }
}

class SkyfireKirinEffect extends OneShotEffect {

    public SkyfireKirinEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may gain control of target creature with that spell's mana value until end of turn";
    }

    public SkyfireKirinEffect(final SkyfireKirinEffect effect) {
        super(effect);
    }

    @Override
    public SkyfireKirinEffect copy() {
        return new SkyfireKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = null;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPermanent) {
                targetCreature = game.getPermanent(target.getFirstTarget());
            }
        }
        if (targetCreature != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetCreature.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
