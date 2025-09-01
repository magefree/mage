package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtistsTalent extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ArtistsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever you cast a noncreature spell, you may discard a card. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {2}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{R}"));

        // Noncreature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellsCostReductionControllerEffect(filter, 1), 2
        )));

        // {2}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{R}"));

        // If a source you control would deal noncombat damage to an opponent or a permanent an opponent controls, it deals that much damage plus 2 instead.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new ArtistsTalentEffect(), 3)));
    }

    private ArtistsTalent(final ArtistsTalent card) {
        super(card);
    }

    @Override
    public ArtistsTalent copy() {
        return new ArtistsTalent(this);
    }
}

class ArtistsTalentEffect extends ReplacementEffectImpl {

    ArtistsTalentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to an opponent " +
                "or a permanent an opponent controls, it deals that much damage plus 2 instead";
    }

    private ArtistsTalentEffect(final ArtistsTalentEffect effect) {
        super(effect);
    }

    @Override
    public ArtistsTalentEffect copy() {
        return new ArtistsTalentEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((DamageEvent) event).isCombatDamage()
                || !source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        return opponents.contains(event.getTargetId())
                || opponents.contains(game.getControllerId(event.getTargetId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }
}
