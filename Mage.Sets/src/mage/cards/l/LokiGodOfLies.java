package mage.cards.l;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class LokiGodOfLies extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets only a single creature");
    private static final FilterCreaturePermanent subfilter = new FilterCreaturePermanent("creature");

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(subfilter));
        subfilter.add(CardType.CREATURE.getPredicate());
    }

    public LokiGodOfLies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell that targets only a single creature, gain control of that creature until end of turn.
        // If it's your turn, untap that creature and it gains haste until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new LokiGodOfLiesEffect(), filter, false));
    }

    private LokiGodOfLies(final LokiGodOfLies card) {
        super(card);
    }

    @Override
    public LokiGodOfLies copy() {
        return new LokiGodOfLies(this);
    }
}

class LokiGodOfLiesEffect extends OneShotEffect {

    LokiGodOfLiesEffect() {
        super(Outcome.Benefit);
        staticText = "gain control of that creature until end of turn. "
            + "If it's your turn, untap that creature and it gains haste until end of turn";
    }

    private LokiGodOfLiesEffect(final LokiGodOfLiesEffect effect) {
        super(effect);
    }

    @Override
    public LokiGodOfLiesEffect copy() {
        return new LokiGodOfLiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }

        Permanent permanent = spell
            .getSpellAbility()
            .getTargets()
            .stream()
            .map(Target::getTargets)
            .flatMap(Collection::stream)
            .map(game::getPermanentOrLKIBattlefield)
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null);
        if (permanent == null) {
            return false;
        }

        game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.processAction();

        if (game.isActivePlayer(player.getId())) {
            permanent.untap(game);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
