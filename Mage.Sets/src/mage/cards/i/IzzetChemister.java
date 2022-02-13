package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IzzetChemister extends CardImpl {

    public IzzetChemister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {R}, {T}: Exile target instant or sorcery card from your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new ExileTargetEffect().setToSourceExileZone(true), new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // {1}{R}, {T}: Sacrifice Izzet Chemister: Cast any number of cards exiled with Izzet Chemister without paying their mana costs.
        ability = new SimpleActivatedAbility(new IzzetChemisterCastFromExileEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private IzzetChemister(final IzzetChemister card) {
        super(card);
    }

    @Override
    public IzzetChemister copy() {
        return new IzzetChemister(this);
    }
}

class IzzetChemisterCastFromExileEffect extends OneShotEffect {

    public IzzetChemisterCastFromExileEffect() {
        super(Outcome.PlayForFree);
        staticText = "cast any number of cards exiled with {this} without paying their mana costs";
    }

    public IzzetChemisterCastFromExileEffect(final IzzetChemisterCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public IzzetChemisterCastFromExileEffect copy() {
        return new IzzetChemisterCastFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (controller == null || exile == null || exile.isEmpty()) {
            return false;
        }
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, new CardsImpl(exile),
                StaticFilters.FILTER_CARD
        );
        return true;
    }
}
