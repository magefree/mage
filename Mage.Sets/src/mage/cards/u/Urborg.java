
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class Urborg extends CardImpl {

    public Urborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {tap}: Target creature loses first strike or swampwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrborgEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Urborg(final Urborg card) {
        super(card);
    }

    @Override
    public Urborg copy() {
        return new Urborg(this);
    }
}

class UrborgEffect extends OneShotEffect {

    UrborgEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature loses first strike or swampwalk until end of turn.";
    }

    UrborgEffect(final UrborgEffect effect) {
        super(effect);
    }

    @Override
    public UrborgEffect copy() {
        return new UrborgEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Ability ability;
        if (player.chooseUse(Outcome.UnboostCreature, "Which ability should be lost?", null, "First Strike", "Swampwalk", source, game)) {
            ability = FirstStrikeAbility.getInstance();
        } else {
            ability = new SwampwalkAbility();
        }
        ContinuousEffect effect = new LoseAbilityTargetEffect(ability, Duration.EndOfTurn);
//        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        return true;
    }
}
