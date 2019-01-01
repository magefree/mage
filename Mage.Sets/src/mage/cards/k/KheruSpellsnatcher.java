package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class KheruSpellsnatcher extends CardImpl {

    public KheruSpellsnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Morph {4}{U}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{4}{U}{U}")));

        // When Kheru Spellthief is turned face up, counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may cast that card without paying its mana cost as long as it remains exiled.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new KheruSpellsnatcherEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    public KheruSpellsnatcher(final KheruSpellsnatcher card) {
        super(card);
    }

    @Override
    public KheruSpellsnatcher copy() {
        return new KheruSpellsnatcher(this);
    }
}

class KheruSpellsnatcherEffect extends OneShotEffect {

    KheruSpellsnatcherEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may cast that card without paying its mana cost as long as it remains exiled";
    }

    KheruSpellsnatcherEffect(final KheruSpellsnatcherEffect effect) {
        super(effect);
    }

    @Override
    public KheruSpellsnatcherEffect copy() {
        return new KheruSpellsnatcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = targetPointer.getFirst(game, source);
        UUID sourceId = source.getSourceId();

        StackObject stackObject = game.getStack().getStackObject(objectId);
        if (stackObject != null
                && game.getStack().counter(targetPointer.getFirst(game, source), source.getSourceId(), game, Zone.EXILED, false, ZoneDetail.NONE)) {
            if (!((Spell) stackObject).isCopy()) {
                MageObject card = game.getObject(stackObject.getSourceId());
                if (card instanceof Card) {
                    ((Card) card).moveToZone(Zone.EXILED, sourceId, game, true);
                    ContinuousEffect effect = new KheruSpellsnatcherCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class KheruSpellsnatcherCastFromExileEffect extends AsThoughEffectImpl {

    KheruSpellsnatcherCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card without paying its mana cost as long as it remains exiled";
    }

    KheruSpellsnatcherCastFromExileEffect(final KheruSpellsnatcherCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KheruSpellsnatcherCastFromExileEffect copy() {
        return new KheruSpellsnatcherCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            if (getTargetPointer().getFirst(game, source) == null) {
                this.discard();
                return false;
            }
            if (sourceId.equals(getTargetPointer().getFirst(game, source))) {
                Card card = game.getCard(sourceId);
                if (card != null) {
                    if (game.getState().getZone(sourceId) == Zone.EXILED) {
                        Player player = game.getPlayer(affectedControllerId);
                        if(player != null) {
                            player.setCastSourceIdWithAlternateMana(sourceId, null, card.getSpellAbility().getCosts());
                            return true;
                        }
                    } else {
                        this.discard();
                    }
                }
            }
        }
        return false;
    }
}
