package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaggadraggaGoregutsBoss extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with a mana ability");
    private static final FilterSpell filter2 = new FilterSpell();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(RaggadraggaGoregutsBossCreaturePredicate.instance);
        filter2.add(RaggadraggaGoregutsBossSpellPredicate.instance);
    }

    public RaggadraggaGoregutsBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Each creature you control with a mana ability gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield, filter, false
        ).setText("Each " + filter.getMessage() + " gets +2/+2")));

        // Whenever a creature you control with a mana ability attacks, untap it.
        this.addAbility(new AttacksAllTriggeredAbility(
                new UntapTargetEffect("untap it"), false, filter,
                SetTargetPointer.PERMANENT, false
        ));

        // Whenever you cast a spell, if at least seven mana was spent to cast it, untap target creature. It gets +7/+7 and gains trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new UntapTargetEffect(), filter2, false
        ).setTriggerPhrase("Whenever you cast a spell, if at least seven mana was spent to cast it, ");
        ability.addEffect(new BoostTargetEffect(7, 7).setText("It gets +7/+7"));
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RaggadraggaGoregutsBoss(final RaggadraggaGoregutsBoss card) {
        super(card);
    }

    @Override
    public RaggadraggaGoregutsBoss copy() {
        return new RaggadraggaGoregutsBoss(this);
    }
}

enum RaggadraggaGoregutsBossCreaturePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAbilities(game).stream().anyMatch(ManaAbility.class::isInstance);
    }
}

enum RaggadraggaGoregutsBossSpellPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return ManaPaidSourceWatcher.getTotalPaid(input.getId(), game) >= 7;
    }
}
