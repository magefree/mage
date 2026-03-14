package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarDestiny extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public AvatarDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 for each creature card in your graveyard and is an Avatar in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(xValue, xValue));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.AVATAR, AttachmentType.AURA
        ).setText("and is an Avatar in addition to its other types"));
        this.addAbility(ability);

        // When enchanted creature dies, mill cards equal to its power. Return this card to its owner's hand and up to one creature card milled this way to the battlefield under your control.
        this.addAbility(new DiesAttachedTriggeredAbility(new AvatarDestinyEffect(), "enchanted creature"));
    }

    private AvatarDestiny(final AvatarDestiny card) {
        super(card);
    }

    @Override
    public AvatarDestiny copy() {
        return new AvatarDestiny(this);
    }
}

class AvatarDestinyEffect extends OneShotEffect {

    AvatarDestinyEffect() {
        super(Outcome.Benefit);
        staticText = "mill cards equal to its power. Return this card to its owner's hand and up to one " +
                "creature card milled this way to the battlefield under your control";
    }

    private AvatarDestinyEffect(final AvatarDestinyEffect effect) {
        super(effect);
    }

    @Override
    public AvatarDestinyEffect copy() {
        return new AvatarDestinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = Optional
                .ofNullable((Permanent) getValue("attachedTo"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        Cards cards = player.millCards(count, source, game);
        game.processAction();
        new ReturnToHandSourceEffect(false, true).apply(game, source);
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD_CREATURE);
        target.withNotTarget(true);
        player.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        player.moveCards(
                game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
        return true;
    }
}
