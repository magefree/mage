
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author cbt33, Plopman (Engineered Plague)
 */
public final class MistformSliver extends CardImpl {

    public MistformSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "{1}: This permanent becomes the creature type of your choice in addition to its other types until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MistformSliverEffect(), new ManaCostsImpl("{1}"));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_SLIVERS)));
    }

    private MistformSliver(final MistformSliver card) {
        super(card);
    }

    @Override
    public MistformSliver copy() {
        return new MistformSliver(this);
    }
}

class MistformSliverEffect extends OneShotEffect {

    public MistformSliverEffect() {
        super(Outcome.Benefit);
        staticText = "This permanent becomes the creature type of your choice in addition to its other types until end of turn";
    }

    public MistformSliverEffect(final MistformSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceCreatureType(permanent);
            if (!player.choose(Outcome.Detriment, typeChoice, game)) {
                return false;
            }
            game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            ContinuousEffect effect = new AddCardSubTypeTargetEffect(SubType.byDescription(typeChoice.getChoice()), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public MistformSliverEffect copy() {
        return new MistformSliverEffect(this);
    }

}
