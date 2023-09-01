package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimalAmulet extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");

    public PrimalAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{4}",
                "Primal Wellspring",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 1)
        ));

        // Whenever you cast an instant or sorcery spell, put a charge counter on Primal Amulet. Then if there are four or more charge counters on it, you may remove those counters and transform it.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new PrimalAmuletEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Primal Wellspring
        // (Transforms from Primal Amulet.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Primal Amulet.)</i>")));

        // Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        this.getRightHalfCard().addAbility(ability);

        // When that mana is spent to cast an instant or sorcery spell, copy that spell and you may choose new targets for the copy.
        this.getRightHalfCard().addAbility(new PrimalWellspringTriggeredAbility(
                ability.getOriginalId(), new CopyTargetSpellEffect(true)
                .setText("copy that spell and you may choose new targets for the copy")
        ));
    }

    private PrimalAmulet(final PrimalAmulet card) {
        super(card);
    }

    @Override
    public PrimalAmulet copy() {
        return new PrimalAmulet(this);
    }
}

class PrimalAmuletEffect extends OneShotEffect {

    PrimalAmuletEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a charge counter on {this}. "
                + "Then if there are four or more charge counters on it, "
                + "you may remove those counters and transform it";
    }

    PrimalAmuletEffect(final PrimalAmuletEffect effect) {
        super(effect);
    }

    @Override
    public PrimalAmuletEffect copy() {
        return new PrimalAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || player == null) {
            return false;
        }
        permanent.addCounters(CounterType.CHARGE.createInstance(), source.getControllerId(), source, game);
        int counters = permanent.getCounters(game).getCount(CounterType.CHARGE);
        if (counters > 3 && player.chooseUse(Outcome.Benefit, "Transform this?", source, game)) {
            permanent.removeCounters("charge", counters, source, game);
            permanent.transform(source, game);
        }
        return true;
    }
}

class PrimalWellspringTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    String abilityOriginalId;

    public PrimalWellspringTriggeredAbility(UUID abilityOriginalId, Effect effect) {
        super(Zone.ALL, effect, false);
        this.abilityOriginalId = abilityOriginalId.toString();
        setTriggerPhrase("When that mana is used to cast an instant or sorcery spell, ");
    }

    public PrimalWellspringTriggeredAbility(final PrimalWellspringTriggeredAbility ability) {
        super(ability);
        this.abilityOriginalId = ability.abilityOriginalId;
    }

    @Override
    public PrimalWellspringTriggeredAbility copy() {
        return new PrimalWellspringTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getData().equals(abilityOriginalId)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !filter.match(spell, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}
