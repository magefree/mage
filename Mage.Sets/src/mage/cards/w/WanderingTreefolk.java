package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.costadjusters.DomainAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author karapuzz14
 */
public final class WanderingTreefolk extends CardImpl {

    public WanderingTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Domain -- {7}{G}: Seek a creature card. This ability costs {1} less to activate for each basic land type among lands you control.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new WanderingTreefolkEffect(),
                new ManaCostsImpl<>("{7}{G}")
        );

        ability.addEffect(new InfoEffect("This ability costs {1} less to activate " +
                "for each basic land type among lands you control."));
        ability.addHint(DomainHint.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.setCostAdjuster(DomainAdjuster.instance);

        this.addAbility(ability);
    }

    private WanderingTreefolk(final WanderingTreefolk card) {
        super(card);
    }

    @Override
    public WanderingTreefolk copy() {
        return new WanderingTreefolk(this);
    }
}

class WanderingTreefolkEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    WanderingTreefolkEffect() {
        super(Outcome.Benefit);
        staticText = "seek a creature card";
    }

    private WanderingTreefolkEffect(final mage.cards.w.WanderingTreefolkEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.w.WanderingTreefolkEffect copy() {
        return new mage.cards.w.WanderingTreefolkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.seekCard(filter, source, game);
    }
}