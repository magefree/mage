package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmittenSwordmaster extends AdventureCard {

    public SmittenSwordmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{B}",
                "Curry Favor",
                new CardType[]{CardType.SORCERY}, "{B}");

        // Smitten Swordmaster
        this.getLeftHalfCard().setPT(2, 1);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Curry Favor
        // You gain X life and each opponent loses X life, where X is the number of Knights you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new CurryFavorEffect());

        finalizeCard();
    }

    private SmittenSwordmaster(final SmittenSwordmaster card) {
        super(card);
    }

    @Override
    public SmittenSwordmaster copy() {
        return new SmittenSwordmaster(this);
    }
}

class CurryFavorEffect extends OneShotEffect {

    CurryFavorEffect() {
        super(Outcome.Benefit);
        staticText = "you gain X life and each opponent loses X life, where X is the number of Knights you control";
    }

    private CurryFavorEffect(final CurryFavorEffect effect) {
        super(effect);
    }

    @Override
    public CurryFavorEffect copy() {
        return new CurryFavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .filter(permanent -> permanent != null && permanent.hasSubtype(SubType.KNIGHT, game))
                .mapToInt(p -> 1)
                .sum();
        new GainLifeEffect(xValue).apply(game, source);
        new LoseLifeOpponentsEffect(xValue).apply(game, source);
        return true;
    }
}