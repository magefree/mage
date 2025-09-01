package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TombOfTheDuskRose extends CardImpl {

    public TombOfTheDuskRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        this.nightCard = true;

        // <i>(Transforms from Profane Procession.)</i>

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}{W}{B}，{T} : Put a creature card exiled with this permanent onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(new TombOfTheDuskRoseEffect(), new ManaCostsImpl<>("{2}{W}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TombOfTheDuskRose(final TombOfTheDuskRose card) {
        super(card);
    }

    @Override
    public TombOfTheDuskRose copy() {
        return new TombOfTheDuskRose(this);
    }
}

class TombOfTheDuskRoseEffect extends OneShotEffect {

    TombOfTheDuskRoseEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "put a creature card exiled with this permanent onto the battlefield under your control";
    }

    private TombOfTheDuskRoseEffect(final TombOfTheDuskRoseEffect effect) {
        super(effect);
    }

    @Override
    public TombOfTheDuskRoseEffect copy() {
        return new TombOfTheDuskRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || exileZone.count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard targetCard = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileZone.getId());
        targetCard.withNotTarget(true);
        controller.choose(outcome, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
