
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
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
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class TombOfTheDuskRose extends CardImpl {

    public TombOfTheDuskRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        this.nightCard = true;

        // <i>(Transforms from Profane Procession.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new InfoEffect("<i>(Transforms from Profane Procession.)</i>")).setRuleAtTheTop(true));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}{W}{B}，{T} : Put a creature card exiled with this permanent onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TombOfTheDuskRoseEffect(), new ManaCostsImpl<>("{2}{W}{B}"));
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

    public TombOfTheDuskRoseEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put a creature card exiled with this permanent onto the battlefield under your control";
    }

    public TombOfTheDuskRoseEffect(final TombOfTheDuskRoseEffect effect) {
        super(effect);
    }

    @Override
    public TombOfTheDuskRoseEffect copy() {
        return new TombOfTheDuskRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && exileId != null && sourceObject != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                TargetCard targetCard = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_CREATURE);
                controller.chooseTarget(outcome, exileZone, targetCard, source, game);
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
