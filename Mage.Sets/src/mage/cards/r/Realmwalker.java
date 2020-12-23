package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class Realmwalker extends CardImpl {

    public Realmwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());

        // As Realmwalker enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells of the chosen type from the top of your library.
        this.addAbility(new SimpleStaticAbility(new RealmwalkerEffect()));
    }

    private Realmwalker(final Realmwalker card) {
        super(card);
    }

    @Override
    public Realmwalker copy() {
        return new Realmwalker(this);
    }
}

class RealmwalkerEffect extends AsThoughEffectImpl {

    public RealmwalkerEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast creature spells of the chosen type from the top of your library.";
    }

    private RealmwalkerEffect(final RealmwalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RealmwalkerEffect copy() {
        return new RealmwalkerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies (UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card cardToCheck = game.getCard(objectId);
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards

        FilterCreatureCard filter = new FilterCreatureCard();
        filter.add(ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game).getPredicate());

        if (cardToCheck != null
                && playerId.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())
                && (!cardToCheck.getManaCost().isEmpty() || cardToCheck.isLand())
                && filter.match(cardToCheck, game)) {
            Player player = game.getPlayer(cardToCheck.getOwnerId());

            UUID needCardID = player.getLibrary().getFromTop(game) == null ? null : player.getLibrary().getFromTop(game).getId();
            return objectId.equals(needCardID);
        }
        return false;
    }
}
