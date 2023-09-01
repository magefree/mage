package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.InfoEffect;
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

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ProfaneProcession extends TransformingDoubleFacedCard {

    public ProfaneProcession(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{W}{B}",
                "Tomb of the Dusk Rose",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // {3}{W}{B}: Exile target creature. Then if there are three or more cards exiled with Profane Procession, transform it.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), ProfaneProcessionCondition.instance,
                "Then if there are three or more cards exiled with {this}, transform it"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Tomb of the Dusk Rose
        // <i>(Transforms from Profane Procession.)</i>
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Profane Procession.)</i>")));

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
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        return exileZone != null && exileZone.size() >= 3;
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
        if (controller == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileId);
        target.setNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
