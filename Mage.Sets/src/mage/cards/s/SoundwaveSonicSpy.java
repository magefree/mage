package mage.cards.s;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.LaserbeakToken;
import mage.game.permanent.token.RavageToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.GenericTargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class SoundwaveSonicSpy extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value equal to the damage dealt from their graveyard"
    );
    private static final FilterSpell oddFilter = new FilterSpell("a spell with an odd mana value");
    private static final FilterSpell evenFilter = new FilterSpell("a spell with an even mana value");

    static {
        oddFilter.add(ManaValueParityPredicate.ODD);
        evenFilter.add(ManaValueParityPredicate.EVEN);
    }

    public SoundwaveSonicSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{1}{W}{U}{B}",
                "Soundwave, Superior Captain",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "WUB");

        // Soundwave, Sonic Spy
        this.getLeftHalfCard().setPT(5, 4);

        // More Than Meets the Eye {2}{W}{U}{B}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{W}{U}{B}"));

        // Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or sorcery card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy without paying its mana cost. If you do, convert Soundwave.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new SoundwaveSonicSpyEffect(), SetTargetPointer.PLAYER, StaticFilters.FILTER_CREATURE_TOKENS, false
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new SoundwaveSonicSpyTargetAdjuster());
        this.getLeftHalfCard().addAbility(ability);

        // Soundwave, Superior Captain
        // Whenever you cast a spell with an odd mana value, convert Soundwave. If you do, create Ravage, a legendary 3/3 black Robot artifact creature token with menace and deathtouch.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new SoundwaveSuperiorCaptainEffect(new RavageToken()), oddFilter, false
        ));

        // Whenever you cast a spell with an even mana value, convert Soundwave. If you do, create Laserbeak, a legendary 2/2 blue Robot artifact creature token with flying and hexproof.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new SoundwaveSuperiorCaptainEffect(new LaserbeakToken()), evenFilter, false
        ));
    }

    private SoundwaveSonicSpy(final SoundwaveSonicSpy card) {
        super(card);
    }

    @Override
    public SoundwaveSonicSpy copy() {
        return new SoundwaveSonicSpy(this);
    }
}

/**
 * Limits the target to a card owned by the damaged player with mana value equal to the total damage dealt,
 * then replaces the player target pointer set by the trigger with the standard {@link FirstTargetPointer}.
 */
class SoundwaveSonicSpyTargetAdjuster extends GenericTargetAdjuster {

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID playerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        int damage = SavedDamageValue.MANY.calculate(game, ability, ability.getEffects().get(0));
        Player player = game.getPlayer(playerId);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());
        if (player == null) {
            return;
        }
        Target newTarget = blueprintTarget.copy();
        Filter filter = newTarget.getFilter();
        filter.add(new OwnerIdPredicate(playerId));
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, damage));
        newTarget.withTargetName(filter.getMessage() + " (mana value " + damage + ", owned by " + player.getLogName() + ")");
        ability.addTarget(newTarget);
    }
}

class SoundwaveSonicSpyEffect extends OneShotEffect {

    SoundwaveSonicSpyEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile target instant or sorcery card with mana value equal to the damage dealt from their graveyard. "
                + "Copy it. You may cast the copy without paying its mana cost. If you do, convert {this}";
    }

    private SoundwaveSonicSpyEffect(final SoundwaveSonicSpyEffect effect) {
        super(effect);
    }

    @Override
    public SoundwaveSonicSpyEffect copy() {
        return new SoundwaveSonicSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        Card cardCopy = game.copyCard(card, source, controller.getId());
        if (!controller.chooseUse(outcome, "Cast copy of " + card.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), Boolean.TRUE);
        boolean cast = controller.cast(
                controller.chooseAbilityForCast(cardCopy, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
        if (cast) {
            new TransformSourceEffect().apply(game, source);
        }
        return true;
    }
}

class SoundwaveSuperiorCaptainEffect extends TransformSourceEffect {

    private final Token token;

    SoundwaveSuperiorCaptainEffect(Token token) {
        super();
        this.token = token;
        staticText = "convert {this}. If you do, create " + token.getDescription();
    }

    private SoundwaveSuperiorCaptainEffect(final SoundwaveSuperiorCaptainEffect effect) {
        super(effect);
        this.token = effect.token.copy();
    }

    @Override
    public SoundwaveSuperiorCaptainEffect copy() {
        return new SoundwaveSuperiorCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return super.apply(game, source) && token.putOntoBattlefield(1, game, source);
    }
}
