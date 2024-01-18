package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KyloxVisionaryInventor extends CardImpl {

    public KyloxVisionaryInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Kylox, Visionary Inventor attacks, sacrifice any number of other creatures, then exile the top X cards of your library, where X is their total power. You may cast any number of instant and/or sorcery spells from among the exiled cards without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new KyloxVisionaryInventorEffect()));
    }

    private KyloxVisionaryInventor(final KyloxVisionaryInventor card) {
        super(card);
    }

    @Override
    public KyloxVisionaryInventor copy() {
        return new KyloxVisionaryInventor(this);
    }
}

class KyloxVisionaryInventorEffect extends OneShotEffect {

    KyloxVisionaryInventorEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of other creatures, then exile the top X cards of your library, " +
                "where X is their total power. You may cast any number of instant and/or sorcery spells " +
                "from among the exiled cards without paying their mana costs";
    }

    private KyloxVisionaryInventorEffect(final KyloxVisionaryInventorEffect effect) {
        super(effect);
    }

    @Override
    public KyloxVisionaryInventorEffect copy() {
        return new KyloxVisionaryInventorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES
        );
        player.choose(outcome, target, source, game);
        Set<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        int xValue = permanents
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        if (cards.isEmpty()) {
            return true;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, cards,
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
        return true;
    }
}
