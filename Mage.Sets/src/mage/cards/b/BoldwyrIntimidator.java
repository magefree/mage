package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BoldwyrIntimidator extends CardImpl {

    public BoldwyrIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoldwyrIntimidatorEffect()));

        // {R}: Target creature becomes a Coward until end of turn.
        Effect effect = new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.COWARD);
        effect.setText("Target creature becomes a Coward until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{R}: Target creature becomes a Warrior until end of turn.
        effect = new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.WARRIOR);
        effect.setText("Target creature becomes a Warrior until end of turn");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BoldwyrIntimidator(final BoldwyrIntimidator card) {
        super(card);
    }

    @Override
    public BoldwyrIntimidator copy() {
        return new BoldwyrIntimidator(this);
    }
}

class BoldwyrIntimidatorEffect extends RestrictionEffect {

    BoldwyrIntimidatorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Cowards can't block Warriors";
    }

    BoldwyrIntimidatorEffect(final BoldwyrIntimidatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        return sourcePermanent != null;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker != null && blocker != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && attacker.hasSubtype(SubType.WARRIOR, game)) {
                return !blocker.hasSubtype(SubType.COWARD, game);
            }
        }
        return true;
    }

    @Override
    public BoldwyrIntimidatorEffect copy() {
        return new BoldwyrIntimidatorEffect(this);
    }
}
