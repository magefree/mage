package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightPawsEmperorsVoice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.AURA, "an Aura");

    static {
        filter.add(LightPawsEmperorsVoicePredicate.instance);
    }

    public LightPawsEmperorsVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an Aura enters the battlefield under your control, if you cast it, you may search your library for an Aura card with mana value less than or equal to that Aura and with a different name than each Aura you control, put that card onto the battlefield attached to Light-Paws, Emperor's Voice, then shuffle.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new LightPawsEmperorsVoiceEffect(), filter));
    }

    private LightPawsEmperorsVoice(final LightPawsEmperorsVoice card) {
        super(card);
    }

    @Override
    public LightPawsEmperorsVoice copy() {
        return new LightPawsEmperorsVoice(this);
    }
}

enum LightPawsEmperorsVoicePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        int zcc = input.getZoneChangeCounter(game);
        Spell spell = game.getStack().getSpell(input.getId());
        return (spell != null && spell.getZoneChangeCounter(game) == zcc - 1)
                || game.getLastKnownInformation(input.getId(), Zone.STACK, zcc - 1) != null;
    }
}

class LightPawsEmperorsVoiceEffect extends OneShotEffect {

    private static enum LightPawsEmperorsVoiceEffectPredicate implements ObjectSourcePlayerPredicate<Card> {
        instance;
        private static final FilterPermanent filter = new FilterControlledPermanent(SubType.AURA);

        @Override
        public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
            return game.getBattlefield()
                    .getActivePermanents(
                            filter, input.getPlayerId(),
                            input.getSourceId(), game
                    ).stream()
                    .filter(Objects::nonNull)
                    .noneMatch(permanent -> CardUtil.haveSameNames(permanent, input.getObject()));
        }
    }

    LightPawsEmperorsVoiceEffect() {
        super(Outcome.Benefit);
        staticText = "if you cast it, you may search your library for an Aura card with mana value " +
                "less than or equal to that Aura and with a different name than each Aura you control, " +
                "put that card onto the battlefield attached to {this}, then shuffle";
    }

    private LightPawsEmperorsVoiceEffect(final LightPawsEmperorsVoiceEffect effect) {
        super(effect);
    }

    @Override
    public LightPawsEmperorsVoiceEffect copy() {
        return new LightPawsEmperorsVoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent aura = (Permanent) getValue("permanentEnteringBattlefield");
        if (player == null || aura == null || !player.chooseUse(
                outcome, "Search for an Aura?", source, game
        )) {
            return false;
        }
        FilterCard filter = new FilterCard("Aura card with mana value less than or equal to " + aura.getManaValue());
        filter.add(SubType.AURA.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, aura.getManaValue() + 1));
        filter.add(LightPawsEmperorsVoiceEffectPredicate.instance);
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (card != null && permanent != null
                && new AuraCardCanAttachToPermanentId(permanent.getId()).apply(card, game)) {
            game.getState().setValue("attachTo:" + card.getId(), permanent);
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            permanent.addAttachment(card.getId(), source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
