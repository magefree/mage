
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WelcomeToTheFold extends CardImpl {

    public WelcomeToTheFold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Madness {X}{U}{U} <i>(If you discard this card
        // discard it into exile. When you do
        // cast it for its madness cost or put it into your graveyard.
        Ability ability = new MadnessAbility(new ManaCostsImpl<>("{X}{U}{U}"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Gain control of target creature if its toughness is 2 or less. If Welcome to the Fold's madness cost was paid, instead gain control of that creature if its toughness is X or less.
        this.getSpellAbility().addEffect(new WelcomeToTheFoldEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private WelcomeToTheFold(final WelcomeToTheFold card) {
        super(card);
    }

    @Override
    public WelcomeToTheFold copy() {
        return new WelcomeToTheFold(this);
    }
}

class WelcomeToTheFoldEffect extends GainControlTargetEffect {

    WelcomeToTheFoldEffect(Duration duration, boolean fixedControl) {
        super(duration, fixedControl);
        staticText = "Gain control of target creature if its toughness is 2 or less. If this spell's madness cost was paid, instead gain control of that creature if its toughness is X or less";
    }

    private WelcomeToTheFoldEffect(final WelcomeToTheFoldEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxToughness = 2;
            ManaCosts manaCosts = source.getManaCostsToPay();
            if (!manaCosts.getVariableCosts().isEmpty()) {
                maxToughness = source.getManaCostsToPay().getX();
            }
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null && permanent.getToughness().getValue() > maxToughness) {
                this.discard();
            }
        }
    }

    @Override
    public WelcomeToTheFoldEffect copy() {
        return new WelcomeToTheFoldEffect(this);
    }

}
