package mage.cards.f;

import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Grath
 */
public final class ForgersFoundry extends CardImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 3 or less");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public ForgersFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {T}: Add {U}. When you spend this mana to cast an instant or sorcery spell with mana value 3 or less, you may exile that spell instead of putting it into its owner's graveyard as it resolves.
        Ability ability = new BlueManaAbility();
        this.addAbility(ability);
        this.addAbility(new ForgersFoundryTriggeredAbility(ability.getOriginalId()));

        // {3}{U}{U}, {T}: You may cast any number of spells from among cards exiled with Forger's Foundry without paying their mana costs. Activate only as a sorcery.
        ability = new SimpleActivatedAbility(new ForgersFoundryCastEffect(), new ManaCostsImpl<>("{3}{U}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ForgersFoundry(final ForgersFoundry card) {
        super(card);
    }

    @Override
    public ForgersFoundry copy() {
        return new ForgersFoundry(this);
    }
}

class ForgersFoundryTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 3 or less");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    String abilityOriginalId;

    public ForgersFoundryTriggeredAbility(UUID abilityOriginalId) {
        super(Zone.ALL, null, false);
        this.abilityOriginalId = abilityOriginalId.toString();
        setTriggerPhrase("When that mana is used to cast an instant or sorcery spell with mana value 3 or less, ");
    }

    private ForgersFoundryTriggeredAbility(final ForgersFoundryTriggeredAbility ability) {
        super(ability);
        this.abilityOriginalId = ability.abilityOriginalId;
    }

    @Override
    public ForgersFoundryTriggeredAbility copy() {
        return new ForgersFoundryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(abilityOriginalId)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, getControllerId(), this, game)) {
                this.getEffects().clear();
                this.addEffect(new ForgersFoundryExileEffect(spell, game));
                return true;
            }
        }
        return false;
    }
}

class ForgersFoundryExileEffect extends ReplacementEffectImpl {

    // we store both Spell and Card to work properly on split cards.
    private final MageObjectReference morSpell;
    private final MageObjectReference morCard;

    ForgersFoundryExileEffect(Spell spell, Game game) {
        super(Duration.OneUse, Outcome.Benefit);
        this.morSpell = new MageObjectReference(spell.getCard(), game);
        this.morCard = new MageObjectReference(spell.getMainCard(), game);
    }

    private ForgersFoundryExileEffect(final ForgersFoundryExileEffect effect) {
        super(effect);
        this.morSpell = effect.morSpell;
        this.morCard = effect.morCard;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(morSpell.getSourceId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCardsToExile(
                sourceSpell, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = morSpell.getSpell(game);
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        return Zone.STACK.equals(zEvent.getFromZone())
                && Zone.GRAVEYARD.equals(zEvent.getToZone())
                && morSpell.refersTo(event.getSourceId(), game) // this is how we check that the spell resolved properly (and was not countered or the like)
                && morCard.refersTo(event.getTargetId(), game); // this is how we check that the card being moved is the one we want.
    }

    @Override
    public ForgersFoundryExileEffect copy() {
        return new ForgersFoundryExileEffect(this);
    }
}

class ForgersFoundryCastEffect extends OneShotEffect {

    ForgersFoundryCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast any number of spells from among cards exiled with " +
                "{this} with total mana value X or less without paying their mana costs";
    }

    private ForgersFoundryCastEffect(final ForgersFoundryCastEffect effect) {
        super(effect);
    }

    @Override
    public ForgersFoundryCastEffect copy() {
        return new ForgersFoundryCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }

        Cards cards = new CardsImpl(exileZone);
        if (player == null || cards.isEmpty()) {
            return false;
        }

        CardUtil.castMultipleWithAttributeForFree(player, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
