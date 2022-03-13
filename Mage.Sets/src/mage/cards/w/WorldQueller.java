
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.*;

/**
 * @author jeffwadsworth
 */
public final class WorldQueller extends CardImpl {

    public WorldQueller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may choose a card type. If you do, each player sacrifices a permanent of that type.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WorldQuellerEffect(), TargetController.YOU, true));

    }

    private WorldQueller(final WorldQueller card) {
        super(card);
    }

    @Override
    public WorldQueller copy() {
        return new WorldQueller(this);
    }
}

class WorldQuellerEffect extends OneShotEffect {

    private static final Set<String> choice = new LinkedHashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.ENCHANTMENT.toString());
        choice.add(CardType.INSTANT.toString());
        choice.add(CardType.LAND.toString());
        choice.add(CardType.PLANESWALKER.toString());
        choice.add(CardType.SORCERY.toString());
        choice.add(CardType.TRIBAL.toString());
    }

    public WorldQuellerEffect() {
        super(Outcome.Benefit);
        staticText = "you may choose a card type. If you do, each player sacrifices a permanent of that type";
    }

    public WorldQuellerEffect(final WorldQuellerEffect effect) {
        super(effect);
    }

    @Override
    public WorldQuellerEffect copy() {
        return new WorldQuellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (player != null && sourceCreature != null) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice);
            if (!player.choose(Outcome.Neutral, choiceImpl, game)) {
                return false;
            }
            CardType type = null;
            String choosenType = choiceImpl.getChoice();
            if (choosenType.equals(CardType.ARTIFACT.toString())) {
                type = CardType.ARTIFACT;
            } else if (choosenType.equals(CardType.LAND.toString())) {
                type = CardType.LAND;
            } else if (choosenType.equals(CardType.CREATURE.toString())) {
                type = CardType.CREATURE;
            } else if (choosenType.equals(CardType.ENCHANTMENT.toString())) {
                type = CardType.ENCHANTMENT;
            } else if (choosenType.equals(CardType.INSTANT.toString())) {
                type = CardType.INSTANT;
            } else if (choosenType.equals(CardType.SORCERY.toString())) {
                type = CardType.SORCERY;
            } else if (choosenType.equals(CardType.PLANESWALKER.toString())) {
                type = CardType.PLANESWALKER;
            } else if (choosenType.equals(CardType.TRIBAL.toString())) {
                type = CardType.TRIBAL;
            }
            if (type != null) {
                FilterControlledPermanent filter = new FilterControlledPermanent("permanent you control of type " + type.toString());
                filter.add(type.getPredicate());

                TargetPermanent target = new TargetControlledPermanent(1, 1, filter, false);
                target.setNotTarget(true);

                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player2 = game.getPlayer(playerId);
                    if (player2 != null && target.canChoose(playerId, source, game)) {
                        while (player2.canRespond() && !target.isChosen() && target.canChoose(playerId, source, game)) {
                            player2.chooseTarget(Outcome.Sacrifice, target, source, game);
                        }
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            chosen.add(permanent);
                        }
                        target.clearChosen();
                    }
                }

                // all chosen permanents are sacrificed together
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                    if (chosen.contains(permanent)) {
                        permanent.sacrifice(source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
