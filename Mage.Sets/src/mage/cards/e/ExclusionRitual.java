package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ExclusionRitual extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ExclusionRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // Imprint - When Exclusion Ritual enters the battlefield, exile target nonland permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExclusionRitualImprintEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.IMPRINT));
        // Players can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExclusionRitualReplacementEffect()));
    }

    private ExclusionRitual(final ExclusionRitual card) {
        super(card);
    }

    @Override
    public ExclusionRitual copy() {
        return new ExclusionRitual(this);
    }
}

class ExclusionRitualImprintEffect extends OneShotEffect {

    ExclusionRitualImprintEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland permanent";
    }

    private ExclusionRitualImprintEffect(final ExclusionRitualImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && sourcePermanent != null && targetPermanent != null) {
            controller.moveCardToExileWithInfo(targetPermanent, getId(), sourcePermanent.getIdName(), source, game, Zone.BATTLEFIELD, true);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        }
        return true;
    }

    @Override
    public ExclusionRitualImprintEffect copy() {
        return new ExclusionRitualImprintEffect(this);
    }
}

class ExclusionRitualReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    ExclusionRitualReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells with the same name as the exiled card";
    }

    private ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        SpellAbility spellAbility = SpellAbility.getSpellAbilityFromEvent(event, game);
        if (spellAbility == null) {
            return false;
        }
        Card card = spellAbility.getCharacteristics(game);
        if (card == null) {
            return false;
        }
        if (sourcePermanent != null && !sourcePermanent.getImprinted().isEmpty()) {
            Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
            if (imprintedCard != null) {
                return CardUtil.haveSameNames(spellAbility.getCharacteristics(game), imprintedCard);
            }
        }
        return false;
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}
