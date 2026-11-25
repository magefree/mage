package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class TheUrSphinx extends CardImpl {

    private static final FilterCard filterSpells = new FilterCard("Sphinx spells");
    private static final FilterPermanent filterAttackers = new FilterCreaturePermanent(SubType.SPHINX, "Sphinxes");

    static {
        filterSpells.add(SubType.SPHINX.getPredicate());
        filterSpells.add(AnotherPredicate.instance);
    }

    public TheUrSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Eminence -- As long as The Ur-Sphinx is in the command zone or on the battlefield, other Sphinx spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filterSpells, 1)
                        .setText("As long as {this} is in the command zone or on the battlefield, "
                                + "other Sphinx spells you cast cost {1} less to cast")
        ).setAbilityWord(AbilityWord.EMINENCE));
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND, new SpellsCostReductionControllerEffect(filterSpells, 1).setText("")
        ));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Sphinxes you control attack, each player mills that many cards. For each player, you may cast a card that player milled this way without paying its mana cost.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new TheUrSphinxEffect(), 1, filterAttackers
        ).setTriggerPhrase("Whenever one or more Sphinxes you control attack, "));
    }

    private TheUrSphinx(final TheUrSphinx card) {
        super(card);
    }

    @Override
    public TheUrSphinx copy() {
        return new TheUrSphinx(this);
    }
}

class TheUrSphinxEffect extends OneShotEffect {

    TheUrSphinxEffect() {
        super(Outcome.PlayForFree);
        staticText = "each player mills that many cards. For each player, you may cast "
                + "a card that player milled this way without paying its mana cost";
    }

    private TheUrSphinxEffect(final TheUrSphinxEffect effect) {
        super(effect);
    }

    @Override
    public TheUrSphinxEffect copy() {
        return new TheUrSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Integer amount = (Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS);
        if (controller == null || amount == null || amount < 1) {
            return false;
        }
        Map<UUID, Cards> milledCards = new LinkedHashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                milledCards.put(playerId, player.millCards(amount, source, game));
            }
        }
        for (Cards cards : milledCards.values()) {
            cards.retainZone(Zone.GRAVEYARD, game);
            CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        }
        return true;
    }
}
