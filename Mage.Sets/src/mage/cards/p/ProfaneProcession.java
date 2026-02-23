package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ProfaneProcession extends TransformingDoubleFacedCard {

    public ProfaneProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{W}{B}",
                "Tomb of the Dusk Rose",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // Profane Procession
        // {3}{W}{B}: Exile target creature. Then if there are three or more cards exiled with Profane Procession, transform it.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), ProfaneProcessionCondition.instance,
                "Then if there are three or more cards exiled with {this}, transform it"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Tomb of the Dusk Rose
        // {T}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());

        // {2}{W}{B}，{T} : Put a creature card exiled with this permanent onto the battlefield under your control.
        ability = new SimpleActivatedAbility(new TombOfTheDuskRoseEffect(), new ManaCostsImpl<>("{2}{W}{B}"));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private ProfaneProcession(final ProfaneProcession card) {
        super(card);
    }

    @Override
    public ProfaneProcession copy() {
        return new ProfaneProcession(this);
    }
}

enum ProfaneProcessionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getExile().getExileZone(CardUtil.getExileZoneId(game, source)))
                .filter(cards -> cards.size() >= 3)
                .isPresent();
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
