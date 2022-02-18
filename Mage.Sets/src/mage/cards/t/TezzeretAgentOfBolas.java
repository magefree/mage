
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TezzeretAgentOfBolas extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public TezzeretAgentOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);

        this.setStartingLoyalty(3);

        // +1: Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(5, 1, filter, true), 1));

        // -1: Target artifact becomes an artifact creature with base power and toughness 5/5.
        Effect effect = new AddCardTypeTargetEffect(Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE);
        effect.setText("Target artifact becomes an artifact creature");
        LoyaltyAbility ability1 = new LoyaltyAbility(effect, -1);
        effect = new SetPowerToughnessTargetEffect(5, 5, Duration.EndOfGame);
        effect.setText("with base power and toughness 5/5");
        ability1.addEffect(effect);
        ability1.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability1);

        // -4: Target player loses X life and you gain X life, where X is twice the number of artifacts you control.
        LoyaltyAbility ability2 = new LoyaltyAbility(new TezzeretAgentOfBolasEffect2(), -4);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

    }

    private TezzeretAgentOfBolas(final TezzeretAgentOfBolas card) {
        super(card);
    }

    @Override
    public TezzeretAgentOfBolas copy() {
        return new TezzeretAgentOfBolas(this);
    }

}

class TezzeretAgentOfBolasEffect2 extends OneShotEffect {

    public TezzeretAgentOfBolasEffect2() {
        super(Outcome.DrawCard);
        staticText = "Target player loses X life and you gain X life, where X is twice the number of artifacts you control";
    }

    public TezzeretAgentOfBolasEffect2(final TezzeretAgentOfBolasEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretAgentOfBolasEffect2 copy() {
        return new TezzeretAgentOfBolasEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT).calculate(game, source, this) * 2;
        if (count > 0) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.loseLife(count, game, source, false);
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(count, game, source);
            }
        }
        return true;
    }

}
