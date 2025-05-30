package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.functions.StackObjectCopyApplier;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ExterminatorMagmarch extends CardImpl {

    public ExterminatorMagmarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell that targets only a single nonland permanent an opponent controls, if another opponent controls one or more nonland permanents that spell could target, choose one of those permanents. Copy that spell. The copy targets the chosen permanent.
        this.addAbility(new ExterminatorMagmarchTriggeredAbility());

        // {1}{B}: Regenerate Exterminator Magmarch.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private ExterminatorMagmarch(final ExterminatorMagmarch card) {
        super(card);
    }

    @Override
    public ExterminatorMagmarch copy() {
        return new ExterminatorMagmarch(this);
    }
}

class ExterminatorMagmarchTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final String OPPONENT_ID_KEY = "ExterminatorMagmarchOpponentKey";
    private static final FilterSpell filter = new FilterInstantOrSorcerySpell(
            "an instant or sorcery spell that targets only a single nonland permanent an opponent controls, " +
                    "if another opponent controls one or more nonland permanents that spell could target"
    );

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
    }

    ExterminatorMagmarchTriggeredAbility() {
        super(new ExterminatorMagmarchEffect(), filter, false);
    }

    private ExterminatorMagmarchTriggeredAbility(final ExterminatorMagmarchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExterminatorMagmarchTriggeredAbility copy() {
        return new ExterminatorMagmarchTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        this.getAllEffects().setValue(
                OPPONENT_ID_KEY,
                game.getSpell(event.getTargetId())
                        .getSpellAbility()
                        .getModes()
                        .values()
                        .stream()
                        .map(Mode::getTargets)
                        .flatMap(Collection::stream)
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .map(Controllable::getControllerId)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null)
        );
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return CardUtil
                .getEffectValueFromAbility(this, OPPONENT_ID_KEY, UUID.class)
                .map(opponentId -> {
                    FilterPermanent filter = StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND.copy();
                    filter.add(Predicates.not(new ControllerIdPredicate(opponentId)));
                    return game.getBattlefield().contains(filter, this, game, 1);
                })
                .orElse(false);
    }

    public static String getKey() {
        return OPPONENT_ID_KEY;
    }
}

class ExterminatorMagmarchEffect extends OneShotEffect {

    private static class ExterminatorMagmarchApplier implements StackObjectCopyApplier {

        private final Iterator<MageObjectReferencePredicate> predicate;

        ExterminatorMagmarchApplier(Permanent permanent, Game game) {
            this.predicate = Arrays.asList(new MageObjectReferencePredicate(permanent, game)).iterator();
        }

        @Override
        public void modifySpell(StackObject stackObject, Game game) {
        }

        @Override
        public MageObjectReferencePredicate getNextNewTargetType() {
            if (predicate.hasNext()) {
                return predicate.next();
            }
            return null;
        }
    }

    ExterminatorMagmarchEffect() {
        super(Outcome.Benefit);
        staticText = "choose one of those permanents. Copy that spell. The copy targets the chosen permanent";
    }

    private ExterminatorMagmarchEffect(final ExterminatorMagmarchEffect effect) {
        super(effect);
    }

    @Override
    public ExterminatorMagmarchEffect copy() {
        return new ExterminatorMagmarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        UUID opponentId = (UUID) getValue(ExterminatorMagmarchTriggeredAbility.getKey());
        if (player == null || spell == null || opponentId == null) {
            return false;
        }
        FilterPermanent filter = StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND.copy();
        filter.add(Predicates.not(new ControllerIdPredicate(opponentId)));
        TargetPermanent target = new TargetPermanent(filter);
        target.withChooseHint("to target with the copy");
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), false,
                1, new ExterminatorMagmarchApplier(permanent, game)
        );
        return true;
    }
}
