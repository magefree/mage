package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.PrismaticAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.PerpetualUtil;
import mage.util.RandomUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class CloneCrafter extends CardImpl {

    public CloneCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, conjure a duplicate of a random creature card from an opponent's library into your hand. It perpetually gains prismatic.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CloneCrafterEffect()));
    }

    private CloneCrafter(final CloneCrafter card) {
        super(card);
    }

    @Override
    public CloneCrafter copy() {
        return new CloneCrafter(this);
    }
}

class CloneCrafterEffect extends OneShotEffect {

    CloneCrafterEffect() {
        super(Outcome.DrawCard);
        staticText = "conjure a duplicate of a random creature card from an opponent's library "
                + "into your hand. It perpetually gains prismatic";
    }

    private CloneCrafterEffect(final CloneCrafterEffect effect) {
        super(effect);
    }

    @Override
    public CloneCrafterEffect copy() {
        return new CloneCrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetOpponent target = new TargetOpponent(true);
        if (controller == null || !controller.choose(outcome, target, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        List<Card> creatures = opponent.getLibrary().getCards(game).stream()
                .filter(card -> card.isCreature(game)).collect(Collectors.toList());
        Card original = RandomUtil.randomFromCollection(creatures);
        if (original == null) {
            return false;
        }
        Card duplicate = ConjureCardEffect.createCard(original.getName());
        if (duplicate == null) {
            return false;
        }
        game.loadCards(Collections.singleton(duplicate), controller.getId());
        PerpetualUtil.addAbility(duplicate, new PrismaticAbility(), source, game);
        return controller.moveCards(duplicate, Zone.HAND, source, game);
    }
}
