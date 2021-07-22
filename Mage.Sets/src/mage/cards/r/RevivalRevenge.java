package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevivalRevenge extends SplitCard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public RevivalRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W/B}{W/B}", "{4}{W}{B}", SpellAbilityType.SPLIT);

        // Revival
        // Return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Revenge
        // Double your life total. Target opponent loses half their life, rounded up.
        this.getRightHalfCard().getSpellAbility().addEffect(new RevivalRevengeEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private RevivalRevenge(final RevivalRevenge card) {
        super(card);
    }

    @Override
    public RevivalRevenge copy() {
        return new RevivalRevenge(this);
    }
}

class RevivalRevengeEffect extends OneShotEffect {

    RevivalRevengeEffect() {
        super(Outcome.Benefit);
        staticText = "Double your life total. Target opponent loses half their life, rounded up.";
    }

    private RevivalRevengeEffect(final RevivalRevengeEffect effect) {
        super(effect);
    }

    @Override
    public RevivalRevengeEffect copy() {
        return new RevivalRevengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        controller.gainLife(controller.getLife(), game, source);
        int life = player.getLife();
        player.loseLife(Math.floorDiv(life, 2) + (life % 2), game, source, false);
        return true;
    }
}