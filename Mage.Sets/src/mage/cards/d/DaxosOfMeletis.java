package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DaxosOfMeletis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public DaxosOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DaxosOfMeletisEffect(), false, true));
    }

    private DaxosOfMeletis(final DaxosOfMeletis card) {
        super(card);
    }

    @Override
    public DaxosOfMeletis copy() {
        return new DaxosOfMeletis(this);
    }
}

class DaxosOfMeletisEffect extends OneShotEffect {

    public DaxosOfMeletisEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top card of that player's library. You gain life equal to that card's mana value. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
    }

    public DaxosOfMeletisEffect(final DaxosOfMeletisEffect effect) {
        super(effect);
    }

    @Override
    public DaxosOfMeletisEffect copy() {
        return new DaxosOfMeletisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (damagedPlayer != null) {
                MageObject sourceObject = game.getObject(source);
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Card card = damagedPlayer.getLibrary().getFromTop(game);
                if (card != null && sourceObject != null) {
                    // move card to exile
                    controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
                    // player gains life
                    controller.gainLife(card.getManaValue(), game, source);
                    // Add effects only if the card has a spellAbility (e.g. not for lands).
                    if (card.getSpellAbility() != null) {
                        // allow to cast the card
                        // and you may spend mana as though it were mana of any color to cast it
                        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
